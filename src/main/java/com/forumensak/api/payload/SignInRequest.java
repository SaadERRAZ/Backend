package com.forumensak.api.payload;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class SignInRequest {
    @NotBlank(message = "Username should not be blank")
    private String usernameOrEmail;


    @NotBlank
    private String password;
}