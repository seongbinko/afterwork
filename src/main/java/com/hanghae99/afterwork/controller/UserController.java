package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.dto.UserRequestDto;
import com.hanghae99.afterwork.dto.UserResponseDto;
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

    private final UserService userService;

    @GetMapping("/api/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserResponseDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {

        return userService.getCurrentUser(userPrincipal);
    }

    @PostMapping("/api/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity modifyUser(@CurrentUser UserPrincipal userPrincipal, @RequestBody UserRequestDto userRequestDto){

        return ResponseEntity.ok().body(userService.modifyUser(userRequestDto, userPrincipal));
    }

    @DeleteMapping("/api/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteUser(@CurrentUser UserPrincipal userPrincipal)
    {
        userService.deleteUser(userPrincipal);

        return ResponseEntity.ok().build();
    }
}
