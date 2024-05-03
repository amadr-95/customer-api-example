# Customer API example

## Overview

This project involves developing an API that exposes endpoints to perform CRUD operations on a Customer model. It starts
with a fake database and progresses to accessing a real PostgreSQL database through Spring Data JPA

## N-Tier Architecture

The application follows an N-tier architecture, with each layer dedicated to a specific responsibility.

- **API layer**: intercepts client requests (GET, POST, PUT, DELETE,...)
- **Business layer**: where all the application logic takes place (throws exceptions, algorithms, ...)
- **DAO layer**: responsible for interacting with the database.

![N-Tier-architecture](https://github.com/amadr-95/customer-api-example/assets/122611230/d972fa57-e830-428e-aa2c-404af3cbce49)


## Application Context

Spring is a _Dependency injection Framework_, which basically means that when we use annotations
like `@Service (@Component), @RestController, @Repository`,
etc... (which are **Beans**) Spring **instantiate and manage these beans for us** and stored
them in the application context.
The moment a class needs one of those dependencies,
**Spring goes to the context application and injects them into the necessary classes**.

![applicationContext](https://github.com/amadr-95/customer-api-example/assets/122611230/d6f963a7-92de-4159-94e9-ba0ef00de62a)


In earlier versions of Spring it was also required to write `@Autowired` annotation on the
constructor, but now it is no longer needed.

## PostgreSQL with Docker compose
To set up the PostgreSQL database using Docker Compose:
```yaml
networks:
  db:
    driver: bridge
volumes:
  db:

services:
  db:
    container_name: postgres
    image: postgres
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      PG_DATA: /data/postgres
    #store data
    volumes:
      - db:/data/postgres
    ports:
      - "5432:5432"
    networks:
      - db
    restart: unless-stopped
```

To start the database container, run:
```bash
docker compose up -d
```

This should pull the postgres image and get the container running.  
Then check if the service is actually running by typing:

```bash
docker compose ps
```

To access the container and create the database:
```bash
docker exec -it postgres bash
```

The container creates an user called postgres by default.
Connect to PostgreSQL server:  
```bash
psql -U postgres
```

### Create the database

Once inside the container, create the database:

```sql
CREATE DATABASE customer;
```

## Configuring datasource properties

```yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5332/customer
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: create-drop
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
    show-sql: true
```

## Mapping Entities

![schema](https://github.com/amadr-95/customer-api-example/assets/122611230/9e3a77c3-3641-4657-bedb-21853bc0476c)


## Spring Data JPA

The `CustomerDAO` have two implementations:

- `CustomerJpaDataAccessService`: which is linked to the real database
- `CustomerListDataAccessService`: which retrieves data from a list acting like a fake database

In the `CustomerService` class, we specifies which one of these implementations we want to
use and inject it. There are two options:

- Mark one implementation as primary with `@Primary` annotation:
  ```java
  @Repository
  @Primary
  public class CustomerJPADataAccessService implements CustomerDAO {...}
  ```
- Use qualifiers to give a name to each implementation and specify that name in `CustomerService` constructor
  ```java
  @Repository
  @Qualifier("jpa")
  public class CustomerJPADataAccessService implements CustomerDAO {...}
  ```

  ```java
  @Repository
  @Qualifier("list")
  public class CustomerListDataAccessService implements CustomerDAO {...}
  ```

  ```java
  @Service
  public class CustomerService {
  
  private final CustomerDAO customerDAO;

    public CustomerService(@Qualifier("jpa") CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
  
  }
  ```

## CRUD Operations

| Method | URL                   | Description                |
|--------|-----------------------|----------------------------|
| GET    | /api/v1/customers     | Retrieve all customers     |
| GET    | /api/v1/customers/:id | Find customer by id        |
| POST   | /api/v1/customers     | Add a new customer         |
| PUT    | /api/v1/customers/:id | Update customer data by id |
| DELETE | /api/v1/customers/:id | Delete customer by id      |
