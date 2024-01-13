FROM openjdk:17-jdk-slim

WORKDIR /app

ADD target/question-service-0.0.1-SNAPSHOT.jar .

EXPOSE 8083

ENTRYPOINT ["java","-jar","/app/question-service-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]
