package com.example.VRMSecurityService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.VRMSecurityService.dto.LoginRequest;
import com.example.VRMSecurityService.dto.LoginResponse;
import com.example.VRMSecurityService.dto.UserResponse;
import com.example.VRMSecurityService.util.JwtUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class SecurityController {
	@Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // Call UserService to validate credentials
        String userServiceUrl = "http://localhost:8083/users/validate";
        ResponseEntity<UserResponse> response = restTemplate.postForEntity(userServiceUrl, request, UserResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            UserResponse user = response.getBody();
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
            return ResponseEntity.ok(new LoginResponse(token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String tokenHeader) {
        try {
            String token = tokenHeader.substring(7); // Remove "Bearer "
            jwtUtil.validateToken(token); // Throws if invalid
            return ResponseEntity.ok("Token is valid");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

}
