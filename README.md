# Assessment - Senior JAVA Engineer

## Requirements

1. JVM version 8
2. Curl library
3. Maven 3+

## Used Libraries

1. Springboot - framework that is backbone of the application - allows DI, application start, assign URL to method
2. Lombok - library that generates access methods, constructors, builders for annotated class - allows to eliminate boilerplate code
3. JSON-P - small library that allows to create string in json format - ideal for not complicated responses
4. JPA - ORM framework - allows to execute queries on database and maps results to class
5. H2 - database - small database that can be included to project - eliminates usage of external database and allows to run project with single command
6. Junit - library is required for unit tests
7. Mockito - provides classes and methods to mock objects in unit tests - unit tests would be impossible to make without it
8. Junit-dataproviders - allows to run single unit test with many configuration for provided arguments - eliminates code duplications in unit test

## Decisions

1. H2 database was selected because it provides ability to run application with single command.
   It stores data to single file or to memory what makes it good solutions for small application.
2. Additional endpoint with url /post/add-dice-rolls was created to provide ability to quickly 
   generate new, reliable data.


## Steps to run application:
1. Open bash console 
2. Move cursor inside Avalog folder
3. Type: 
    ```bash
       mvn clean install
    ```
4. Wait for build to complete 
5. Type
   ```bash 
       java -jar target/avalog-assessment.jar
   ```

## List of endpoints

1. /get/dice-rolls - endpoint that returns a dice distribution simulation

2. /post/add-dice-rolls - endpoint that executes a dice distribution simulation and saves it to database

3. /get/simulation-and-rolls - endpoint that returns the total number of simulations and total rolls made, grouped by all existing dice number–dice side 

4. /get/relative-distribution - endpoint that returns relative distribution for a given dice number–dice side combination, compared to the total rolls, for all the simulations 

## Testing

To test every endpoint please use http request with curl library.

Curl request examples for each endpoint:
1. /get/dice-rolls (without query string)
    ```bash
        curl -s -X GET 'http://localhost:8080/get/dice-rolls'
    ```
2. /get/dice-rolls (with query string)
    ```bash
        curl -s -X GET 'http://localhost:8080/get/dice-rolls?number_of_rolls=1&number_of_dices=2&number_of_sides=4'
    ```
3. /post/add-dice-rolls
    ```bash
        curl -s -X POST 'http://localhost:8080/post/add-dice-rolls' --data 'number_of_rolls=100&number_of_dices=7&number_of_sides=5'
    ```
4. /get/simulation-and-rolls
    ```bash
        curl -s -X GET 'http://localhost:8080/get/simulation-and-rolls'
    ```
5. /get/relative-distribution
    ```bash
       curl -s -X GET 'http://localhost:8080/get/relative-distribution?number_of_dices=10&number_of_sides=6' 
    ```
