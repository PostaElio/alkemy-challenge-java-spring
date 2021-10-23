package com.example.api_disney.controller.payload;

import java.util.Date;

public class MovieCompactResponse {
    private String image;
    private String title;
    private Date creationdate;

    public MovieCompactResponse(String image, String title, Date cration_date) {
        this.image = image;
        this.title = title;
        this.creationdate = cration_date;
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
}
