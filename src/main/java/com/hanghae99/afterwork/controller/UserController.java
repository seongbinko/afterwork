package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.dto.*;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/api/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserResponseDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        return new UserResponseDto(
                user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getImageUrl(),
                user.getOffTime(),
                user.getLocations().stream().map(
                        location -> new LocationResponseDto(
                                location.getLocationId(),
                                location.getName()
                        )
                ).collect(Collectors.toList()),
                user.getInterests().stream().map(
                        interest -> new InterestResponseDto(
                                interest.getInterestId(),
                                interest.getCategory().getCategoryId()
                        )
                ).collect(Collectors.toList()),
                user.getCollects().stream().map(
                        collect -> new CollectResponseDto(
                                collect.getCollectId(),
                                collect.getProduct().getProductId()
                        )
                ).collect(Collectors.toList())
        );
    }

    @PostMapping("/api/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity modifyUser(@CurrentUser UserPrincipal userPrincipal, @RequestBody UserRequestDto userRequestDto){

        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        return ResponseEntity.ok().body(userService.modifyUser(userRequestDto, user));
    }

    @DeleteMapping("/api/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteUser(@CurrentUser UserPrincipal userPrincipal)
    {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
