package com.example.apiDisney.controller.payload;

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
    //private Long movie_id;

    /*
    "image":"imagen",
    "name":"name",
    "age": 2,
    "weight": 20.5,
    "history": "wqekljwqekwqjelqwke"
    */
}
