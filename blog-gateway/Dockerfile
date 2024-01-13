FROM openjdk:17-jdk-slim

WORKDIR /app

ADD target/blog-gateway-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/blog-gateway-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]
