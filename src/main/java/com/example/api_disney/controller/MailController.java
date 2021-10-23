package com.example.api_disney.controller;

import com.example.api_disney.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping()
    public void wea() throws  Exception{
        //String yourFirstName,String yourLastName, String yourUsername,String yourEmail
        mailService.sendTextEmail("Elio","Posta","elWatonQloFome","postaelio76@gmail.com");
    }

}
