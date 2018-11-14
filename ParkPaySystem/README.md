To run this application you should have maven, git, and java jdk.

First start by `updating` apt-get
```
sudo apt-get update
```

Then install java jdk, maven, and git

For Ubuntu 16.04 use command to get the latest java jdk 
```
sudo apt-get install openjdk-8-jdk
```

Install git by using this command
```
sudo apt-get install git
```

Install maven by using this command
```
sudo apt-get install maven
```

Get the project
```
git clone https://github.com/Makk-Attack/CS445
```

Open the project directory
```
cd CS445/ParkPaySystem
```

Build maven project and run test using 
```
mvn clean test
```

Generate coverage using jacoco
```
mvn jacoco:report
```

Open test report with a browser, this report is found in `target/site/jacoco/index.html`
In Ubuntu 16.04 you can use this command to open the report made by jacoco
```
xdg-open target/site/jacoco/index.html
```
Finally run the application using Spring Boot
```
mvn spring-boot:run
```
This command starts a tomcat server at port 8080. The application can be accessed from `localhost:8080/parkpay`


