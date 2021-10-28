# Api-Disney

### This is a project for [Alkemy](https://www.alkemy.org/).

To know more about the use of the api key read the documentation [Postman](https://documenter.getpostman.com/view/16938604/UV5deuXD)

Remember to put your own configuration in [application.properties](https://github.com/PostaElio/alkemy-challenge-java-spring/blob/main/src/main/resources/application.properties)

```properties
spring.datasource.url=jdbc:postgresql://localhost:1000/disney-data
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update

jwt.secret: secretkey123
```

If you want to use [SenderGrid](https://sendgrid.com/) then you will need to sign up for SenderGrid to get your own Api-key.
Once you have your own Api-key, u will need create the file **sendgrid.env** and stored your Api-key inside

```java
SendGrid sg = new SendGrid("SENDGRID_API_KEY");
```