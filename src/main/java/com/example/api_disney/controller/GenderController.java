package com.example.api_disney.controller;

import com.example.api_disney.service.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genders")
public class GenderController {

    @Autowired
    private GenderService genderService;

    /**********-- SAVE/UPDATE(with ID in json) - GENDER --************/


    /**********-- DELETE - GENDER --************/

}
