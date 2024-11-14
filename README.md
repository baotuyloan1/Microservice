The reason is microservices architecture makes applications easier to scale, faster to develop, enable innovation and it is also going to help organizations by accelerating the time to take their new enhancements to the market.
With all these reasons, microservice is going to be a demanding skill set for next few years.

AGENDA:
- Welcome to the world of Microservices
- Building microservices business lgoic using Spring Boot
- How to we right size our microservices & identify boundaries.
- How to containerize our microservices using Docker
- Configurations Management in microservices using Spring Cloud config
- Service Discovery & Service Registration in microservices using Eureka
- Building an edge server for microservices using Spring Cloud Gateway.
- Making Microservices Resilient using Resiliency4j patterns.
- Observability and monitoring of microservices using Grafana, Prometheus ...
- Securing microservice using OAuth2/OpenID, Spring Security
- Event Driven microservices using RabbitMQ, Spring Cloud Functions & Stream
- Event Driven microservices using Kafka, Spring Cloud Functions & Stream
- Container Orchestration using Kubernates
- Deep dive on Helm (kubernates package manager)
- Deploying microservices into cloud kubernates cluster
- Many best practices, techniques followed by real time microservice developers.



[EvolutionOfMicroServicesArchitecture.md](Accounts%2FEvolutionOfMicroServicesArchitecture.md)

We need to build REST Services.
Using REST services, we can establish synchronous communication between multiple APIS or multiple services or multiple web applications.
Synchronous communication means when a request comes from an external application to my microservice
The external application is going to wait for my response so that it can proceed to the next request.
Off course, synchronous communication is not the only option that we have to build microservices.
This is the mostly commonly used approach in this project.

**Implementing REST Services**

REST(Representational state transfer)services are one of the most often encountered ways to implement communication between two web apps.
REST offers access to functionality the server exposes through endpoints a client can call.

**SWAGGER API:**
Accounts : http://localhost:8080/swagger-ui/index.html
Loans:  http://localhost:8090/swagger-ui/index.html
Cards:  http://localhost:9000/swagger-ui/index.html

**IMPORTANT ANNOTATIONS AND CLASSES that supports building REST Services**
- @RestController - can be used to put on top of a call. This expose your methods as REST APIs. Developers can also use @Controller + @ResponseBody for same behavior.
- @ResponseBody - can be used on top of a method to build a Rest API when we are using @Controller on top of a Jav class
- @ControllerAdvice - is used to mark the class as a REST controller advice. Along with @ExceptionHandler, this can be used to handle exceptions globally inside app. We have another annotation @RestControllerAdvice which is same as @ControllerAdvice + @ResponseBody

*Classes*
- RequestEntity<T> - Allows developers to receive the request body, header in a HTTP request.
- ResponseEntity<T> - Allow developers to send response body, status, and headers on the HTTP response
- @RequestHeader & @RequestBody - is used to receive the request body and header individually.


**BUILDING MICROSERVICES**

One of the most challenging aspects of building a successful microservices system is the identification of proper microservice boundaries and defining the size of each microservice.
Below are the mose common followed approaches in the industry:
- Domain-Driven Sizing: Since many of our modifications or enhancements driven by the business needs, we can size/define boundaries of our microservices that are closely aligned with Domain-Driven design & Business capabilities. But this process takes lot of time and need good domain knowledge
  - Disadvantage: it's time-consuming and it also needs a lot of people who has good understanding on the business and domain to overcome these challenges
  - Advantage: 
- Event Storming Sizing: conducting an interactive fun session among various stake holder to identify the list of important evens in the system like 'Completed Payment', 'Search for a Product' etc. Base on the events we can identify 'Commands', ' Reactions' and can try to group them to a domain-driven services.
  - Advantage: Fast, straightforward, Engaging,Effective
    - reference: https://www.lucidchart.com/blog/ddd-event-storming

  
![img_9.png](img_9.png)

- Anyone who wants to communicate with my microservice, first they need to talk with my API gateway.
- Using API gateway, my client application, they can invoke my rest APIs or microservices
- All these microservices, including API gateway is deployed as a Docker containers inside a Kubernetes cluster.
- Each microservice, they can have their own supporting database. They can follow their own programming language or framework.
- Can also implement event streaming with the help of Event Bus like Kafka, RabbitMQ.
- Whenever an authentication trying to happen, you can trigger an SMS to the customer for his OTP, so that you can do asynchronously with the help of event streaming. Similarly, whenever an order is confirmed, you can send an SMS or email to the customer asynchronously with the help of event streaming.

*GENERATE DOCKER IMAGES*
- Dockerfile -> accounts
- Buildpacks (maven using Buildpacks) -> loans
  - Buildpacks is a project initiated and developed by Heroku and Pivotal based upon the best practices
  - Google Jib (open source with Java Tool, and with the Maven plugin command) ->cards