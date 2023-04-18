FROM openjdk:17

COPY target/hotel-application-0.0.1-SNAPSHOT.jar hotel-application.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/hotel-application.jar"]

