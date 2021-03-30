package com.forumensak.api.controller;

import com.forumensak.api.payload.SignInRequest;
import com.forumensak.api.payload.SignUpRequest;
import com.forumensak.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest loginRequest) {
        return authService.signIn(loginRequest);
    }

    @PostMapping("/signup/{roleId}")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, @PathVariable long roleId) {
        return authService.signUp(signUpRequest, roleId);
    }

    @PostMapping("/verify/{token}")
    public ResponseEntity<?> verifyAccount(@PathVariable String token) {
        return authService.verifyAccount(token);
    }


}
