package com.example.VRMUserService.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;

import com.example.VRMUserService.dto.LoginRequest;
import com.example.VRMUserService.dto.UserRequest;
import com.example.VRMUserService.dto.UserResponse;
import com.example.VRMUserService.dto.ValidateUserRequest;
import com.example.VRMUserService.dto.ValidateUserResponse;
import com.example.VRMUserService.entity.User;
import com.example.VRMUserService.service.UserService;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
    private UserService userService;
	
	@GetMapping("/get")
    public Page<User> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        return userService.getUsersPaginated(page, size);
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
    
    @PostMapping("/validate")
    public ResponseEntity<ValidateUserResponse> validateUser(@RequestBody ValidateUserRequest request) {
        Optional<User> userOptional = userService.getUserByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // password check
            if (userService.getPasswordEncoder().matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.ok(new ValidateUserResponse(user.getEmail(), user.getRole()));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
