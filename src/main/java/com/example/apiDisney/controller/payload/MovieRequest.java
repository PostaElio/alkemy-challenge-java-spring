package com.example.apiDisney.controller.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data

public class MovieRequest {
    private String image;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date creationdate;
    private int clasification;

    /*
    "image":"path",
    "title":"titulo",
    "creationdate": "20-12-2000",
    "clasification": "2"
     */
}
