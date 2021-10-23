package com.example.api_disney.service;

import com.sendgrid.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailService {

    public void sendTextEmail(String yourFirstName,String yourLastName, String yourUsername,String yourEmail) throws IOException {
        Email from = new Email("postaelio76@gmail.com");
        String subject = "Welcome "+yourUsername+" to the best Disney Api!";
        Email to = new Email(yourEmail);
        Content content = new Content("text/plain", "Welcome "+yourFirstName+" "+yourLastName+". Now you can use us API.");
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid("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");
        //SendGrid sg = new SendGrid("SENDGRID_API_KEY");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(request.getBody());
        } catch (IOException ex) {
           throw ex;
        }
    }
}
