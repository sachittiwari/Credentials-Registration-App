# Credentials Registration Client

## Overview

This project contains all the front end code for client side of Credentials Registration Application.
The user needs to login via single sign-on using Google Account
The user needs to associate himself to one or more organizations during the first time that he logs.
Subsequently, the user can select any organization for the session(if more than one) and manage credentials belonging to that organization.

### Pre-requisites

Before running the application, please ensure the below components are installed and available

- **NodeJS 22.11.0**: This application is developed using node v22.11.0. Please ensure that it is already installed.
- **Backend Application**: The backend application must be up and running. URL can be viewed in `.env` file.
- **Google OAuth Creds**: OAuth credentials need to be created in Google Cloud. The client id is to be configured in `.env` file.

### Steps to run the application in local

- **Import Source Code**: Clone this repository from Git and import in your preferred IDE.
- **Install Dependencies**: Install dependencies using the below command
  ```shell
  npm install
  ```
- **Run the Application**: Build the application using the below command

  ```shell
  npm start
  ```

- **Access the Application**: The application can be accessed using the below URL
  ```shell
  http://localhost:3000
  ```
