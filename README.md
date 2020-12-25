# TopScore 

## Build Environment
* JDK 1.8
* Spring-boot
* Gradle
* H2 Database

## Project Setup
* Java 8 installed and configured

## Run Project
1. Using IDE
    * Import the project in the IDE
    * Run the application using the runner class
2. Using Command line
    * From the project root location open a command line
    * Run this command to build the application
        ~~~
        gradlew build
        ~~~
   * A JAR file will be created in ``root/build/libs``
   * Run the JAR file using this command
        ~~~
        java -jar ./build/libs/topScore-*.jar
        ~~~
## Curl Commands
#### Create Score
``Request:``
~~~
curl --location --request POST 'localhost:9090/score/add' \
--header 'Content-Type: application/json' \
--data-raw '{
    "score": integer,
    "name": "string",
    "time": "yyyy-mm-ddThh:mm"
}'
~~~
``Response:``
~~~
{
    "id": integer,
    "name": "NAME",
    "score": integer,
    "time": "yyyy-mm-ddThh:mm:00.000+00:00"
}
~~~
#### Get Score by ID
``Request:``
~~~
curl --location --request GET 'localhost:9090/score/get/{id}'
~~~
``Response``
~~~
{
    "id": integer,
    "name": "NAME",
    "score": integer,
    "time": "yyyy-mm-ddThh:mm:00.000+00:00"
}
~~~
#### Get List of Score
``Request filter with Name``
~~~
curl --location --request POST 'localhost:9090/score/get/list' \
--header 'Content-Type: application/json' \
--data-raw '{
    "filterBy": "name",
    "palayerName": [
        "PLAYER_NAME_1",
        "PLAYER_NAME_2"
    ]
}'
~~~

``Request filter with Time``
~~~
curl --location --request POST 'localhost:9090/score/get/list' \
--header 'Content-Type: application/json' \
--data-raw '{
    "filterBy": "time",
    "time": "yyyy-dd-mmTHH:mm",
    "timeCondition": "after"
}'
~~~
``Response``
~~~
{
    "content": [
        {
            "id": 1,
            "name": "PLAYER_NAME_1",
            "score": integer,
            "time": "yyyy-mm-ddTHH:mm:00.000+00:00"
        },
        {
            "id": 2,
            "name": "PLAYER_NAME_1",
            "score": 30,
            "time": "yyyy-mm-ddTHH:mm:00.000+00:00"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 5,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalElements": 2,
    "totalPages": 1,
    "size": 5,
    "number": 0,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "numberOfElements": 3,
    "first": true,
    "empty": false
}
~~~

#### Get Player History
``Request``
~~~
curl --location --request GET 'localhost:9090/score/get/history/PLAYER_NAME'
~~~
``Response:``
~~~
{
    "average": integer,
    "highest": {
        "id": integer,
        "name": "string",
        "score": integer,
        "time": "yyyy-mm-ddThh:mm:00.000+00:00"
    },
    "history": [
        {
            "id": integer,
            "name": "string",
            "score": integer,
            "time": "yyyy-mm-ddThh:mm:00.000+00:00"
        },
        {
            "id": integer,
            "name": "string",
            "score": integer,
            "time": "yyyy-mm-ddThh:mm:00.000+00:00"
        },
        {
            "id": integer,
            "name": "string",
            "score": integer,
            "time": "yyyy-mm-ddThh:mm:00.000+00:00"
        }
    ],
    "lowest": {
        "id": integer,
        "name": "string",
        "score": integer,
        "time": "yyyy-mm-ddThh:mm:00.000+00:00"
    }
}
~~~