# Credentials Registration Service

## Overview
This backend service provides APIs for the management of Credentials.
It has three main entities - User, Organization and Credentials

It provides CRUD Operations for the above entities that can be utilized from front end.

## How to Run the Application
The below steps provide step-by-step guide on how to run the credentials registration service.

### Pre-requisites
Before running the application, please ensure the below components are installed and available

- **Java 21**: This application is developed using Java 21. Please ensure that it is installed either directly into the system or via the IDE.
- **Maven**: This application is built using Maven. Please ensure that it is installed and available in the system.
- **PostgreSQL**: This application uses PostgreSQL as the database. Please ensure that it is installed and running on the system.

### Steps to run the Application
- **Import Source Code**: Clone this repository from Git and import in your preferred IDE.
- **Setting Up Database**:
    - Create a new database in PostgreSQL with the name `credentials_registration`.
    - Update the `application.properties` file with the database connection details.
- **Build the Application**: Build the application using the below command
    ```shell
    mvn clean install
- **Run the Application**: Run the application using the below command
    ```shell
    mvn spring-boot:run
-  You can also run the application by navigating to the `CredentialsRegistrationApplication` class and running it as a Java Application.
-  Another way to run the application is by navigating to the targets folder of the project and running the below command.
    ```shell
    java -jar CredentialsRegistration-0.0.1-SNAPSHOT.jar
- **Access the Application**: The application can be accessed using the below URL
    ```shell
    http://localhost:8085/swagger-ui.html
