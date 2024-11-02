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




**What are Microservices?**


We can call these microservices as one of the architecture style that we can consider to build our web applications.


To understand microservices, let's imagine a bank called EazyBank
Typically, banks comprise various departments, including Accounts, Cards, and Loans.
For these EasyBank, in order to build a web application, there are multiple architecture patterns available inside the industry.

The very first are traditional approach that we have to build web application is Monolith

**The Monolith**

All the applications used to be deployed as a Single unit where all functionality deployed together inside a single server.
We call this architecture approach as *Monolith*.

*Props*:

- Simpler development and deployment for smaller teams and applications.
- Fewer cross-cutting concerns (all your non-functional requirements like security, auditing, logging). This is going to be simple inside monolithic because all your code deployed inside a single server as a web application.
- Better performance due to no network latency.