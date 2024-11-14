#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

#Information around who maintains the image
MAINTAINER github.com/baotuyloan1

# Add the application's jar to the container
COPY target/Accounts-0.0.1-SNAPSHOT.jar Accounts-0.0.1-SNAPSHOT.jar

# Whenever someone is trying to generate a container from my docker image, it is going to execute this command
ENTRYPOINT ["java","-jar","Accounts-0.0.1-SNAPSHOT.jar"]