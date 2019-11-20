# Spring Boot Example

Short description
-------------------------------
This is a simple Web client that utilises REST API service to retrieve weather information for given location.

Rough illustration of the data flow:

      Web frontend     <-[JSON]->     REST API service      <-[XML]->       OpenWeatherMap (external free weather API)
 

Execution Procedure
------------------------
- Clone the repository
- Change directory to your local working copy
- Run the following commands:
```
$ mvn clean package
$ java -jar service\target\service-0.0.1-SNAPSHOT.jar
$ java -jar web\target\web-0.0.1-SNAPSHOT.jar
```

By default, the service module will be deployed on 8081 and the web module on 8080. If you would like to change these configurations, please change the port in application.properties (available in both web and service module).

Enter: http://localhost:8080

There is a single defined user using Spring InMemoryUserDetailsManager. You can login with username: "guest" and password: "password"


 