FROM openjdk:17-jdk-slim

WORKDIR /app

ADD target/chat-service-0.0.1-SNAPSHOT.jar .

EXPOSE 8085

ENTRYPOINT ["java","-jar","/app/chat-service-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]
