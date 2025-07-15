package com.example.VRMUserService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

import com.example.VRMUserService.dto.LoginRequest;
import com.example.VRMUserService.dto.UserDTO;
import com.example.VRMUserService.dto.UserRequest;
import com.example.VRMUserService.dto.UserResponse;
import com.example.VRMUserService.entity.User;
import com.example.VRMUserService.exception.UserAlreadyExistsException;
import com.example.VRMUserService.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {
	
	 	@Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	    @Autowired
	    private KafkaProducer kafkaProducer;
	    
	    public UserResponse registerUser(UserRequest request) {
	        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
	            throw new UserAlreadyExistsException("Email already registered.");
	        }

	        User user = new User();
	        user.setFirstName(request.getFirstName());
	        user.setLastName(request.getLastName());
	        user.setEmail(request.getEmail());
	        user.setPassword(passwordEncoder.encode(request.getPassword()));
	        user.setMobile(request.getMobile());
	        user.setRole(request.getRole());

	        userRepository.save(user);
	        
	        try {
	            UserDTO userDto = new UserDTO(
	                user.getUserId(),
	                user.getFirstName(),
	                user.getLastName(),
	                user.getEmail()
	            );

	            ObjectMapper mapper = new ObjectMapper();
	            String userJson = mapper.writeValueAsString(userDto);
	            kafkaProducer.sendUserCreatedEvent(userJson);

	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }


	        return mapToResponse(user);
	    }

	    public String login(LoginRequest request) {
	        User user = userRepository.findByEmail(request.getEmail())
	                .orElseThrow(() -> new RuntimeException("Invalid email"));

	        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	            throw new RuntimeException("Invalid password");
	        }

	        return "Login successful as " + user.getRole();
	    }

	    private UserResponse mapToResponse(User user) {
	        UserResponse res = new UserResponse();
	        res.setUserId(user.getUserId());
	        res.setFirstName(user.getFirstName());
	        res.setLastName(user.getLastName());
	        res.setEmail(user.getEmail());
	        res.setMobile(user.getMobile());
	        res.setRole(user.getRole());
	        return res;
	    }

	    public Optional<User> getUserByEmail(String email) {
	        return userRepository.findByEmail(email);
	    }
	    
	    public PasswordEncoder getPasswordEncoder() {
	        return passwordEncoder;
	    }

		public Page<User> getUsersPaginated(int page, int size) {
        //Pageable pageable = PageRequest.of(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("firstName").ascending());
        return userRepository.findAll(pageable);
    }

	
		public boolean deleteUser(Long id) {
		    if (userRepository.existsById(id)) {
		        userRepository.deleteById(id);
		        return true;
		    }
		    return false;
		}



}
