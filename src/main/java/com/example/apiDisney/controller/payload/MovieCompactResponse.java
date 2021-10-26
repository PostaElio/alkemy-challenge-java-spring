package com.example.apiDisney.controller.payload;

import lombok.Data;

import java.util.Date;

@Data
public class MovieCompactResponse {
    private String image;
    private String title;
    private Date creationdate;

    public MovieCompactResponse(String image, String title, Date cration_date) {
        this.image = image;
        this.title = title;
        this.creationdate = cration_date;
    }

}
