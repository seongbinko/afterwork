package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.dto.CollectRequestDto;
import com.hanghae99.afterwork.dto.CollectResponseDto;
import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.model.Collect;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.model.User;
import com.hanghae99.afterwork.repository.CollectRepository;
import com.hanghae99.afterwork.repository.UserRepository;
import com.hanghae99.afterwork.security.CurrentUser;
import com.hanghae99.afterwork.security.UserPrincipal;
import com.hanghae99.afterwork.service.CollectService;
import com.hanghae99.afterwork.validator.CollectRequestDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class CollectController {

    private final CollectRequestDtoValidator collectRequestDtoValidator;
    private final CollectService collectService;
    private final CollectRepository collectRepository;
    private final UserRepository userRepository;

    @InitBinder("collectRequestDto")
    public void postCollectBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(collectRequestDtoValidator);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/api/collects")
    public ResponseEntity postCollect(@Valid @RequestBody CollectRequestDto collectRequestDto, Errors errors, @CurrentUser UserPrincipal userPrincipal){
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        Collect collect = collectService.postCollect(collectRequestDto, userPrincipal);
        CollectResponseDto collectResponseDto = new CollectResponseDto(collect);
        return ResponseEntity.ok().body(collectResponseDto);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/api/collects")
    public ResponseEntity deleteAllCollect(@CurrentUser UserPrincipal userPrincipal){
        collectService.deleteAllCollect(userPrincipal);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/api/collects/{collectId}")
    public ResponseEntity deleteOneCollect(@PathVariable Long collectId, @CurrentUser UserPrincipal userPrincipal){
        Collect collect = collectService.deleteOneCollect(collectId, userPrincipal);
        CollectResponseDto collectResponseDto = new CollectResponseDto(collect);
        return ResponseEntity.ok().body(collectResponseDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/collects")
    public ResponseEntity getAllCollect(@CurrentUser UserPrincipal userPrincipal){
        List<ProductResponseDto> collectsList = collectService.getAllCollect(userPrincipal);
        return ResponseEntity.ok().body(collectsList);
    }
}


