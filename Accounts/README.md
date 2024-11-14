```text
mvn clean install
```
- This command will tell to my maven to compile my spring boot application base on pom.xml.
- This command will also do a small basic unit testing, and if the unit testing is successful, it is going to generate a jar.
*NOTE*: Make sure you already have installed maven on your machine.
![img.png](img.png), This file name is based on pom.xml file.
![img_1.png](img_1.png)

All the dependencies related to accounts microservice is going to be available inside this jar except Java runtime (include embedded server from `spring-boot-starter-web`) 
![img_2.png](img_2.png)

**RUN A JAR WITH THE HELP OF MAVEN**
`mvn spring-boot:run` on command line will start the application.
Because I have a plugin with the name `spring-boot-maven-plugin` in pom.xml, this plugin is looking for a jar inside my target folder and using the same jar.
It is going to start my web application


**RUN A JAR WITH THE HELP OF JAVA**
`java -jar target/accounts-0.0.1-SNAPSHOT.jar`


**BUILD A DOCKER IMAGE**
```text
docker build . -t baotuyloan1/accounts:241114
```

![img_3.png](img_3.png)



**RUN ACCOUNTS MIRCOSERVICES AS A DOCKER CONTAINER**
- By default, all docker containers are going start inside their own isolated network and we can't access the services inside your Docker network because it is deployed inside its own private network.
- So that's why not to get them access from the external network, like from your local system or from any other system, you need to expose them explicitly with the help of this port mapping.
```text
docker run -d -p 8080:8080 baotuyloan1/accounts:241114 
```
- (first 8080): is my local port and (second 8080): is the port inside my container
- I'm telling to my DOcker server is my Docker container is going to start at a port 8080. First port, docker exposes the container to the outside of Docker network at the port (8080). So anyone who wants to communicate with this container from outside the Docker network, they have to use a port which is 8080.

![img_4.png](img_4.png)

```text
docker ps
```
- This command will show you all the running containers in your Docker network
```text
docker ps -a 
```
- This command will show you all the containers (including the stopped containers) in your Docker network

```text
docker start <container_id>
```
- This command will start a stopped container
```text
docker stop <container_id>
```
- This command will stop a running container

**PORT MAPPING IN DOCKER**
By default, containers are connected to an isolated network within the Docker host. TO access a container from your local network,
you need to configure port mapping explicitly. For instance, when running the accounts Service application, we can provide the port mapping as an argument in the docker run command:
`-p 8081:8080` (where the first value represents the external port and the second value represents the container port).
Below diagram demonstrates the functionality of this configuration

![img_5.png](img_5.png)

**RUNNING A SPRING BOOT APP AS A CONTAINER USING DOCKERFILE**
Steps to be followed:
1. Run the maven command, "mvn clean install" from the location where pom.xml is present to generate the jar file inside target folder.
2. Write instructions to Docker inside a file with the name Dockerfile to generate a Docker image. Sample instructions are mentioned below.
![img_6.png](img_6.png)
3. Execute the docker command "docker build . -t baotuyloan1/accounts:241114"from the location where Dockerfile is present. This will generate the docker image based on the tag name provided.
4. Execute the docker command "docker run -p 8080:8080 baotuyloan1/accounts:241114" to start the container. This will start the docker container based on the docker image name and port mapping provided