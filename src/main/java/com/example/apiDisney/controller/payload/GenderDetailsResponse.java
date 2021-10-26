package com.example.apiDisney.controller.payload;

import com.example.apiDisney.model.MovieEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GenderDetailsResponse {
    private String name;
    private String image;
    private List<MovieEntity> movieEntities = new ArrayList<MovieEntity>();
}
