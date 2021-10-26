package com.example.apiDisney.controller.payload;

import lombok.Data;

@Data

public class SignUpRequest {
    private String first_name;
    private String last_name;
    private String username;
    private String email;
    private String password;

}
