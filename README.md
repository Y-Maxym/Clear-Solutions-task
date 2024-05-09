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
| 3   | Code is covered by unit tests using Spring                                                                                           |      |
| 4   | Code has error handling for REST                                                                                                     | ✅    |
| 5   | API responses are in JSON format                                                                                                     | ✅    |
| 6   | Use of database is not necessary. The data persistence layer is not required.                                                        | ✅    |
| 7   | Any version of Spring Boot. Java version of your choice.                                                                             | ✅    |
| 8   | You can use Spring Initializer utility to create the project: Spring Initializr.                                                     | ✅    |


To run the application you need to run the docker-compose file and the application will be deployed in docker on port 8080 with a database, the volume of which will be located in the root folder of the project.

All variables can be controlled from the .env file

By following the link http://localhost:8080/swagger-ui/index.html you can see all available endpoints and test them