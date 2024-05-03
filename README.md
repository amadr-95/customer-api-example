# Customer API example

## Overview

Developing an API that exposes endpoints to perform CRUD operations within Customer model,
starting with a fake database to finish accessing to a real PostgreSQL database through Spring Data JPA.

## N-Tier Architecture

One layer per each class to separate concerns.

- **API layer**: to intercept the request from the client (GET, POST, PUT, DELETE,...)
- **Business layer**: where all the application logic takes place (throws exceptions, algorithms, ...)
- **DAO layer**: responsible to interact with the database.

[image]

## Application Context

Spring is a _Dependency injection Framework_, which basically means that when we use annotations
like `@Service (@Component), @RestController, @Repository`,
etc... (which are **Beans**) Spring **instantiate and manage these beans for us** and stored
them in the application context.
The moment a class needs one of those dependencies,
**Spring goes to the context application and injects them into the necessary classes**.

[image]

In earlier versions of Spring it was also required to write `@Autowired` annotation on the
constructor, but now it is no longer needed.

## PostgreSQL with Docker compose

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

To have this database up and running:
`
docker compose up -d
`. This should pull the postgres image and get the container running.  
Then check if the service is actually running by typing
`docker compose ps`

To access the container and create the database:
`docker exec -it postgres bash`

The container creates an user called postgres by default which
have to be connect to Postgre server:  
`psql -U postgres`

### Create the database

Once inside the container the database can be created:

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

[imagen]

## Spring Data JPA

At this point we have two implementations of CustomerDAO:

- CustomerJpaDataAccessService: which is linked to the real database
- CustomerListDataAccessService: which retrieves data from a list acting like a fake database

In CustomerService class we have to specified which one of these implementations we want to
use to be injected. We have two options:

- Mark one implementation as primary with `@Primary` annotation:
  ```
  @Repository
  @Primary
  public class CustomerJPADataAccessService implements CustomerDAO {...}
  ```
- Use qualifiers giving a name to each one and specify that name in CustomerService constructor
  ```
  @Repository
  @Qualifier("jpa")
  public class CustomerJPADataAccessService implements CustomerDAO {...}
  ```

  ```
  @Repository
  @Qualifier("list")
  public class CustomerListDataAccessService implements CustomerDAO {...}
  ```

  ```
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
