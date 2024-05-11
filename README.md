| #   | info                                                                                                                                 | Done |
|-----|--------------------------------------------------------------------------------------------------------------------------------------|------|
| 1.1 | Email (required). Add validation against email pattern                                                                               | ✅    |
| 1.2 | First name (required)                                                                                                                | ✅    |
| 1.3 | Last name (required)                                                                                                                 | ✅    |
| 1.4 | Birth date (required). Value must be earlier than current date                                                                       | ✅    |
| 1.5 | Address (optional)                                                                                                                   | ✅    |
| 1.6 | Phone number (optional)                                                                                                              | ✅    |
| 2.1 | Create user. It allows to register users who are more than [18] years old.                                                           | ✅    |
| 2.2 | Update one/some user fields                                                                                                          | ✅    |
| 2.3 | Update all user fields                                                                                                               | ✅    |
| 2.4 | Delete user                                                                                                                          | ✅    |
| 2.5 | Search for users by birth date range. Add the validation which checks that “From” is less than “To”. Should return a list of objects | ✅    |
| 3   | Code is covered by unit tests using Spring                                                                                           | ✅    |
| 4   | Code has error handling for REST                                                                                                     | ✅    |
| 5   | API responses are in JSON format                                                                                                     | ✅    |
| 6   | Use of database is not necessary. The data persistence layer is not required.                                                        | ✅    |
| 7   | Any version of Spring Boot. Java version of your choice.                                                                             | ✅    |
| 8   | You can use Spring Initializer utility to create the project: Spring Initializr.                                                     | ✅    |

## How to test

To run the application you need to run the docker-compose file and the application will be deployed in docker on port 8080 with a database, the volume of which will be located in the root folder of the project.
<br>
The application image is also present on docker hub under the name ondecember/clear-solutions:1.0

If you don't have docker, then the application is hosted on the link https://clear-solutions-1-0.onrender.com, you can also connect to it. You may need to wait a little, because the first request may be a little long

All variables can be controlled from the .env file if you are using local version.

By following the [link](http://localhost:8080/swagger-ui/index.html) you can see all available endpoints and test them. If you are using a hosted application, then you need to follow this [link](https://clear-solutions-1-0.onrender.com/swagger-ui/index.html)

Also if you are using Postman for API testing then you can use file [Collection](Clear-Solutions.postman_collection.json) to import the collection for testing endpoints. If you are using the hosting version, then you need to change the base_url in the collection variables from http://localhost:8080 to https://clear-solutions-1-0.onrender.com
![image](https://github.com/Y-Maxym/Clear-Solutions-task/assets/121685289/349b45a6-dd62-4544-a7bc-acf4ca9ae7a6)

### Technologies used
This is a small RESTful API application that includes basic CRUD operations to create, read, modify and delete a user.

The application is written in Spring boot.<br>
The application uses Spring Web to create the REST API.<br>
Lombok to hide boilerplate code and make it easy to write code.<br>
Spring Data JPA to create JPA based repository, also based on Hibernate ORM to bind entity to table.<br>
Mapstruct for easy mapping from dto to entity and vice versa.<br>
Spring Validation for validating the dto.<br>
PostgreSQL was used as database and liquibase was also used for easy management of database migration.<br>
Springdoc openapi for creating enpoints documentation.<br>
Spring test, H2, JUnit, Mockito, Testcontainers used for writing tests.<br>

### Class descriptions

UserRestControllerV1 - controller that receives requests, processes them and gives a response in JSON format, in some cases the response can be body-less. It has enpoints for creating, receiving, updating and deleting users.

UserServiceImpl - implementation of UserService. Service that includes all business logic. It has methods for creating, retrieving, updating and deleting users, as well as a method for generating the URI link that comes in the Location header response to user creation.

UserRepository - a repository that has a connection to the database and can perform operations with it. In addition to the standard methods it has an additional method to search for users by date of birth.

UserCreateDto, UserResponseDto, UserUpdateDto - dto layer for convenient hiding of entity service fields.

User - entity that represents a user, uses Hibernate and JPA annotations to bind to database tables.

UserMapper - a mapper that is used for mapping from dto to entity and vice versa.

AgeValidator - A validator that uses the ValidAge annotation to validate age based on the user's date of birth and current date.

ValidAge - An annotation that is placed over a field to validate it.

BindingResultService - a service that accommodates a method to generalize BindingResult processing to handle validation errors.

ApplicationException - an exception that is a parent to other exceptions for easy storage.<br>
SimpleApplicationException - A simplified version of an ApplicationException.<br>
IncorrectDateException, UserCreationException, UserNotFoundException, UserUpdateException - custom exceptions.

ErrorEntity, FieldErrorEntity, SimpleErrorEntity - representation of exception objects to be sent in case of errors.

GlobalExceptionHandler - exception handler.
