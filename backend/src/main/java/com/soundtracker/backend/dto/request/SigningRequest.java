package com.soundtracker.backend.dto.request;

import lombok.Data;

@Data
public class SigningRequest {
    private String username;
    private String password;
}
