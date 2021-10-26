package com.example.apiDisney.controller.payload;

import com.example.apiDisney.model.GenderEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class MovieWEA {
    private Long id;
    private String image;
    private String title;
    private Date creationdate;
    private int clasification;
    private List<GenderEntity> genderEntities = new ArrayList<GenderEntity>();
}
