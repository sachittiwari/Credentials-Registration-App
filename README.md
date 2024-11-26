# Credentials Registration Application

## Overview
This application is a full stack application that contains four componenets:
- **Front End**: Developed in REACT using VITE
- **Back End**: Developed using Java and Spring Boot
- **Database**: PostgreSQL 15
- **Envoy**: Envoy proxy as API gateway for backend

## Pre-requisites
Docker needs to be installed

## Build and pull Docker Image
- **Front End**: Refer the `Dockerfile` inside creds-registration-client project for building the front end image. Ensure the front end is built using `npm run build`. Go to this folder and execute below command to create docker image:
  ```shell
    docker build -t credentials-registration-client:1.0 .
- **Back End**: Refer the `Dockerfile` inside creds-registration-service project for building the back end image. Ensure `maven clean install` is done before building the image. Go to the backend folder and execute below command to build the image:
  ```shell
    docker build -t credentials-registration-service:1.0 .
- **Database**: Download the postgres image from docker hub using below command:
  ```shell
    docker pull postgres
- **Envoy**: Download the image for envoy proxy using below command:
  ```shell
    docker pull envoyproxy/envoy:tools-v1.32.1

## Run containers
  `docker-compose.yaml` present inside creds-registration-service can be used to run all the images as separate containers. `envoy.yaml` present inside the same folder contains configuration for envoy proxy. Go to the folder and run below command:
  ```shell
    docker-compose -f docker-compose.yaml up
