# EmpBook

Experience a new way of managing employees with ReactJS, Spring Boot and MongoDB.

![EmpBook](/img/empbook.png "EmpBook")

### Step 1 - Pre-requisite

You need the following:

- **Maven**
- **MongoDB** running on localhost on port 27017


### Step 2 - Run the App

First, clone the repo with  the following command:

` $ git clone https://github.com/schabiyo/EmpBook.git `


Then change the configuration file to point to your running mongoDB instance. The application has 2 profiles defines: One for running the application with a MongoDB running locally and another one with a remote MongoDB instance like Atlas.
By default, the application expect a mongoDB server running on localhost on port 27017. If you have a different setup, please change the application configuration file: 

Then start the application by running the following command:

` $ cd EmpBook `

To run the application in local mode change the configuration:


Then run the following command:

``` 
---
 spring:
   profiles: local
   data:
     mongodb:
       uri: mongodb://localhost:27017
```

` $ mvn spring-boot:run `

To run the application with Atlas, change the atlas profile and run start the application as follow:
Make sure to add include your current IP in your Atlas project IP Whitelist.

``` 
---
spring:
  profiles: atlas
  data:
    mongodb:
      uri: mongodb://mongod3.eastus.cloudapp.azure.com:27000,mongod2.eastus.cloudapp.azure.com:27000,mongod1.eastus.cloudapp.azure.com:27000/test?ssl=false&replicaSet=rs1
```


` $ mvn spring-boot:run  -Dspring.profile.active=atlas`

