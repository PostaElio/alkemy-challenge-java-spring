package com.example.api_disney.controller.payload;

import com.example.api_disney.model.MovieEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDetailResponse {
    private String image;
    private String name;
    private int age;
    private float weight;
    private String history;
    private List<MovieEntity> movieEntities = new ArrayList<MovieEntity>();

}
