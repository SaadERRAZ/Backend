package com.forumensak.api.controller;

import com.forumensak.api.payload.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping("/hello")
    public ApiResponse Hello()
    {
        System.out.println("ok");return new ApiResponse(true,"Api is working!");
    }
}
