package com.example.api_disney.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String first_name;
    private String last_name;
    private String username;
    private String email;
    private String password;

}
