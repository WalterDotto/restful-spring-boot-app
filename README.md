![](https://img.shields.io/badge/Spring%20boot-2.2.7-green)
![](https://img.shields.io/badge/Junit-5-green)
![](https://img.shields.io/badge/PostgreSQL-9.6-green)


# Spring CRUD app
Java Spring boot project using Spring Data JPA module with basic setup.


To fully run this application you'll first need to build the docker image in which the postgreSQL database will be running.

Go to docker folder and inside exec the following command:

```docker build . --tag spring-jpa-test```

To run the docker image type:

```docker run -p8081:5432 spring-jpa-test```

The database engine will be listening on your machine's 8081 port.

To access to database client run:

```docker exec -it containerId psql -U root -d springtest```


Finally to run go to the source app folder and type:

1) ```mvn clean install```
2) ```mvn spring-boot:run```

Go to: localhost:8080/crud-demo

