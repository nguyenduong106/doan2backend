package com.travelapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.travelapp.controller.util.PaginationUtil;
import com.travelapp.exception.ResourceNotFoundException;
import com.travelapp.model.Tour;
import com.travelapp.model.User;
import com.travelapp.payload.TourDetailRequest;
import com.travelapp.payload.TourDetailRequests;
import com.travelapp.payload.UserDetailRequest;
import com.travelapp.payload.UserIdentityAvailability;
import com.travelapp.payload.UserProfile;
import com.travelapp.payload.UserSummary;
import com.travelapp.repository.UserRepository;

import com.travelapp.security.CurrentUser;
import com.travelapp.security.UserPrincipal;

@RestController
@RequestMapping("/api")
public class UserController {
	private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @GetMapping("/user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }
    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public User getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));


        return user;
    }
    @PutMapping("/user")
    public ResponseEntity<User> editUser(@RequestBody User user){
        if (user.getId()==null){
            throw new ResourceNotFoundException("User","ID",user.getUsername());
        }else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User userEdited=userRepository.save(user);
            return new ResponseEntity<>(userEdited,null, HttpStatus.OK);
        }

    }
}
