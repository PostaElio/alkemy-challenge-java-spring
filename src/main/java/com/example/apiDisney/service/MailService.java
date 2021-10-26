package com.example.apiDisney.service;

import java.io.IOException;


public interface MailService {

    void sendTextEmail(String yourFirstName,String yourLastName, String yourUsername,String yourEmail) throws IOException;

}
