package com.hanghae99.afterwork.controller;

import com.hanghae99.afterwork.dto.FeedbackRequestDto;
import com.hanghae99.afterwork.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/api/feedback")
    public ResponseEntity createFeedback(@Valid @RequestBody FeedbackRequestDto feedbackRequestDto, Errors errors){

        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        feedbackService.createFeedback(feedbackRequestDto);

        return ResponseEntity.ok().build();
    }
}
