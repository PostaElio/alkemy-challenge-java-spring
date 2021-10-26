package com.example.apiDisney.service.impl;

import com.example.apiDisney.service.MailService;
import com.sendgrid.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailServiceImpl implements MailService {

    @Override
    public void sendTextEmail(String yourFirstName, String yourLastName, String yourUsername, String yourEmail) throws IOException {
            Email from = new Email("postaelio76@gmail.com");
            String subject = "Welcome "+yourUsername+" to the best Disney Api!";
            Email to = new Email(yourEmail);
            Content content = new Content("text/plain", "W<elcome "+yourFirstName+" "+yourLastName+". Now you can use us AP>I.");
            Mail mail = new Mail(from, subject, to, content);
            SendGrid sg = new SendGrid("SENDGRID_API_KEY");
            Request request = new Request();
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                Response response = sg.api(request);

            } catch (IOException ex) {
                throw ex;
            }
        }

}
