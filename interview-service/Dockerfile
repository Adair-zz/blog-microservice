FROM openjdk:17-jdk-slim

WORKDIR /app

ADD target/interview-service-0.0.1-SNAPSHOT.jar .

EXPOSE 8082

ENTRYPOINT ["java","-jar","/app/interview-service-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]
