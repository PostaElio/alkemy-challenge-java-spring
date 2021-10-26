package com.example.apiDisney.controller.payload;

import com.example.apiDisney.model.CharacterEntity;
import com.example.apiDisney.model.GenderEntity;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class MovieDetailResponse {
    private Long id;
    private String image;
    private String title;
    private Date creationdate;
    private int clasification;
    /*
    private List<CharacterEntity> characterEntities = new ArrayList<CharacterEntity>();
    private List<GenderEntity> genderEntities = new ArrayList<GenderEntity>();*/
    private Set<CharacterEntity> characterEntities = new HashSet<CharacterEntity>();
    private Set<GenderEntity> genderEntities = new HashSet<GenderEntity>();

}
