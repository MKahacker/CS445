# CS 445 Final Project Pay to Park application

This application is written in **Java** and I am using **Maven** as the project manager. 


## Setup For the Project
To run this application you should have maven, git, and java jdk.

First start by `updating` apt-get
```
"sudo apt-get update"
```

Then install **java jdk**
For Ubuntu 16.04 use command to get the latest java jdk 
```
"sudo apt-get install openjdk-8-jdk"
```

Install **git** if you don't already have it by using this command
```
"sudo apt-get install git"
```

Install **maven** by using this command
```
"sudo apt-get install maven"
```

## Get and test the project
1. Get the project
```
"git clone https://github.com/Makk-Attack/CS445"
```
2. Open the project directory
```
"cd CS445/ParkPaySystem"
```
3. Build **maven** project and run test using 
```
"mvn clean test"
```
4. Generate coverage using **jacoco**
```
"mvn jacoco:report"
```

5. Open test report with a browser, this report is found in `target/site/jacoco/index.html`
   In Ubuntu 16.04 you can use this command to open the report made by **jacoco**
```
"xdg-open target/site/jacoco/index.html"
```

## Run the project
6. Finally run the application using **Spring Boot** with the comand
```
"mvn spring-boot:run"
```
This command starts a tomcat server at port 8080. The application can be accessed from `localhost:8080/parkpay`


