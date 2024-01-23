FROM openjdk:17-jdk-slim

WORKDIR /app

ADD target/customer-service-0.0.1-SNAPSHOT.jar .

EXPOSE 8086

ENTRYPOINT ["java","-jar","/app/customer-service-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]
