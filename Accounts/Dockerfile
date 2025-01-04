#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Fixing unhealth issue when starting the container by using docker-compose.file
# Update package list and install curl
RUN apt-get update && apt-get install -y curl

# Clean up to reduce image size
RUN apt-get clean && rm -rf /var/lib/apt/lists/*


#Information around who maintains the image
MAINTAINER github.com/baotuyloan1

# Add the application's jar to the container
COPY target/Accounts-0.0.1-SNAPSHOT.jar Accounts-0.0.1-SNAPSHOT.jar

# Whenever someone is trying to generate a container from my docker image, it is going to execute this command
ENTRYPOINT ["java","-jar","Accounts-0.0.1-SNAPSHOT.jar"]

