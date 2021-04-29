package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.dto.UserRequestDto;
import com.hanghae99.afterwork.exception.ResourceNotFoundException;
import com.hanghae99.afterwork.model.User;
import com.hanghae99.afterwork.repository.UserRepository;
import com.hanghae99.afterwork.security.CurrentUser;
import com.hanghae99.afterwork.security.UserPrincipal;
import com.hanghae99.afterwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/api/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @PostMapping("/api/user")
    public ResponseEntity modifyUser(@CurrentUser UserPrincipal userPrincipal, @RequestBody UserRequestDto userRequestDto){

        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        return ResponseEntity.ok().body(userService.modifyUser(userRequestDto, user));
    }

    @DeleteMapping("/api/user")
    public ResponseEntity deleteUser(@CurrentUser UserPrincipal userPrincipal)
    {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
