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
