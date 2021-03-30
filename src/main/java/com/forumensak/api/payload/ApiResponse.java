package com.forumensak.api.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse {
    private final Boolean success;
    private final String message;
}
