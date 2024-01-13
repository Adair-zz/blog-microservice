FROM openjdk:17-jdk-slim

WORKDIR /app

ADD target/code-service-0.0.1-SNAPSHOT.jar .

EXPOSE 8084

ENTRYPOINT ["java","-jar","/app/code-service-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]
