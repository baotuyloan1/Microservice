#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Fixing unhealth issue when starting the container by using docker-compose.file
# Update package list and install curl
RUN apt-get update && apt-get install -y curl

# Clean up to reduce image size
RUN apt-get clean && rm -rf /var/lib/apt/lists/*

# Use for OpenTelemetry Java Agent,
# Create the 'libs' directory
RUN mkdir -p /app/libs
# we need to download the opentelemetry-javaagent-2.11.0.jar.
# use the jar file inside the container. Because when build a jar, if we declare this in the pom file, the jar file will only be available inside the file .jar
RUN curl -L -o /app/libs/opentelemetry-javaagent-1.33.5.jar \
    https://repo1.maven.org/maven2/io/opentelemetry/javaagent/opentelemetry-javaagent/1.33.5/opentelemetry-javaagent-1.33.5.jar

#Information around who maintains the image
MAINTAINER github.com/baotuyloan1

# Add the application's jar to the container
COPY target/Accounts-0.0.1-SNAPSHOT.jar Accounts-0.0.1-SNAPSHOT.jar

# Whenever someone is trying to generate a container from my docker image, it is going to execute this command
ENTRYPOINT ["java","-jar","Accounts-0.0.1-SNAPSHOT.jar"]

