package com.example.api_disney.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequest {
    private String image;
    private String title;
    private Date creationdate;
    private int clasification;

    /*
    "image":"path",
    "title":"titulo",
    "creationdate": "20/12/2000",
    "clasification": "2"
     */
}
