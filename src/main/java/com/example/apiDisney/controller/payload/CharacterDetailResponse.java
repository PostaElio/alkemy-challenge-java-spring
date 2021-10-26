package com.example.apiDisney.controller.payload;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CharacterDetailResponse {
    private Long id;
    private String image;
    private String name;
    private int age;
    private float weight;
    private String history;
    //private List<MovieWEA> movieEntities = new ArrayList<MovieWEA>();
    private Set<MovieWEA> movieEntities = new HashSet<MovieWEA>();
}
