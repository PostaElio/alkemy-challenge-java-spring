# Api-Disney

### This is a project for [Alkemy](https://www.alkemy.org/)

To know more about the use of this Api-Rest read the documentation in [Postman](https://documenter.getpostman.com/view/16938604/UV5deuXD).

Remember to put your own configuration in [application.properties](https://github.com/PostaElio/alkemy-challenge-java-spring/blob/main/src/main/resources/application.properties).

```properties
spring.datasource.url=jdbc:postgresql://localhost:1000/disney-data
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update

jwt.secret: secretkey123
```

If you want recive a mail when you are registered in the this Api u will need to sign up for [SenderGrid](https://sendgrid.com/) to get your own Api-key.
Once you have your own Api-key, u will need create the file **sendgrid.env** and stored your Api-key inside, or edit the file [MailServiceImpl](https://github.com/PostaElio/alkemy-challenge-java-spring/blob/main/src/main/java/com/example/apiDisney/service/impl/MailServiceImpl.java) and put your own Api-key in "SENDGRID_API_KEY".

```java
SendGrid sg = new SendGrid("SENDGRID_API_KEY");
```

### Dependencies
- Spring Web
- Spring Security
- JPA
- PSQL
- JWT
- Send Grid
- Model Maper
- Lombok