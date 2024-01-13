FROM openjdk:17-jdk-slim

WORKDIR /app

ADD target/user-service-0.0.1-SNAPSHOT.jar .

EXPOSE 8081

ENTRYPOINT ["java","-jar","/app/user-service-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]
