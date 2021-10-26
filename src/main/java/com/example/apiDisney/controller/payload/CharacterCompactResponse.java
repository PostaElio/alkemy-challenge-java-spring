package com.example.apiDisney.controller.payload;

import lombok.Data;

@Data
public class CharacterCompactResponse {
    private String image;
    private String name;

    public CharacterCompactResponse(String image, String name) {
        this.image = image;
        this.name = name;
    }

}
