package com.travelapp.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.travelapp.exception.AppException;
import com.travelapp.model.Role;
import com.travelapp.model.RoleName;
import com.travelapp.model.User;
import com.travelapp.payload.ApiResponse;
import com.travelapp.payload.JwtAuthenticationResponse;
import com.travelapp.payload.LoginRequest;
import com.travelapp.payload.SiginUpRequest;
import com.travelapp.repository.RoleRepository;
import com.travelapp.repository.UserRepository;
import com.travelapp.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		Authentication authentication=authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword())
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt=jwtTokenProvider.generateToke(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SiginUpRequest siginUpRequest){
		if (userRepository.existsByUsername(siginUpRequest.getUsername())) {
			return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),HttpStatus.BAD_REQUEST);
		}
		if(userRepository.existsByEmail(siginUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
		   User user = new User(siginUpRequest.getName(), siginUpRequest.getUsername(),
				   siginUpRequest.getEmail(), siginUpRequest.getPassword());
		   
		   user.setPassword(passwordEncoder.encode(user.getPassword()));
		   Role userRole=roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("User Role not set."));
		   user.setRoles(Collections.singleton(userRole));
		   User result=userRepository.save(user);
		   URI location=ServletUriComponentsBuilder
				   .fromCurrentContextPath().path("/api/users/{username}")
	                .buildAndExpand(result.getUsername()).toUri();
	        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));

	}
}
