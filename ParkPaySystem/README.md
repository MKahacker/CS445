# CS 445 Final Project Pay to Park application

This application is written in **Java** and I am using **Maven** as the project manager. 


## Setup For the Project
To run this application you should have maven, git, and java jdk.

First start by `updating` apt-get
```
sudo apt-get update
```

Then install **java jdk**
For Ubuntu 16.04 use command to get the latest java jdk 
```
sudo apt-get install openjdk-8-jdk
```

Install **git** if you don't already have it by using this command
```
sudo apt-get install git
```

Install **maven** by using this command
```
sudo apt-get install maven
```

## Get and test the project
1. Get the project
```
git clone https://github.com/Makk-Attack/CS445
```
2. Open the project directory
```
cd CS445/ParkPaySystem
```
3. Build **maven** project and run test using 
```
mvn clean test
```
4. Generate coverage using **jacoco**
```
mvn jacoco:report
```

5. Open test report with a browser, this report is found in `target/site/jacoco/index.html`
   In Ubuntu 16.04 you can use this command to open the report made by **jacoco**
```
xdg-open target/site/jacoco/index.html
```

## Run the project
6. Finally run the application using **Spring Boot** with the command
```
mvn spring-boot:run
```
This command starts a tomcat server at port 8080. The application can be accessed from `localhost:8080/parkpay`

## License

Copyright (c) 2018 Mahmoud Kahack 



Permission is hereby granted, free of charge, to any person obtaining a copy

of this software and associated documentation files (the "Software"), to deal

in the Software without restriction, including without limitation the rights

to use, copy, modify, merge, publish, distribute, sublicense, and/or sell

copies of the Software, and to permit persons to whom the Software is

furnished to do so, subject to the following conditions:



The above copyright notice and this permission notice shall be included in all

copies or substantial portions of the Software.



THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR

IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,

FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE

AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER

LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,

OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE

SOFTWARE.

## Known Bug
The search feature returns all orders when searching for key="car". 

