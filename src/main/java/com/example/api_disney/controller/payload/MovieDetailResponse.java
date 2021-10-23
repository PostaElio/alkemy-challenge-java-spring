package com.example.api_disney.controller.payload;

import com.example.api_disney.model.CharacterEntity;
import com.example.api_disney.model.GenderEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieDetailResponse {
    private Long id;
    private String image;
    private String title;
    private Date creationdate;
    private int clasification;
    private List<CharacterEntity> characterEntities = new ArrayList<CharacterEntity>();
    private List<GenderEntity> genderEntities = new ArrayList<GenderEntity>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public int getClasification() {
        return clasification;
    }

    public void setClasification(int clasification) {
        this.clasification = clasification;
    }

    public List<CharacterEntity> getCharacterEntities() {
        return characterEntities;
    }

    public void setCharacterEntities(List<CharacterEntity> characterEntities) {
        this.characterEntities = characterEntities;
    }
    public List<GenderEntity> getGenderEntities() {
        return genderEntities;
    }

    public void setGenderEntities(List<GenderEntity> genderEntities) {
        this.genderEntities = genderEntities;
    }
}
