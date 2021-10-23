package com.example.api_disney.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterRequest {
    private String image;
    private String name;
    private int age;
    private float weight;
    private String history;
}
