## Book App - Team 3

Book App is a full-stack application which allows users 
to sign up, search for, and record their favorite books.

### Requirements
- Jdk 11
- Npm
- Angular
- MySQL
- Docker
- Minikube

### Installation
To run this repo, simply clone it locally.

The Java microservices must all be built using `mvn clean package -DskipTests`

The Angular frontend must be prepared by running `npm install`

Build the docker images for each service using `docker build -t <image-name> .

Create kubernetes deployment and service configuration files

Deploy each service to kubernetes using `kubectl create -f <config file name>`

Enable and add ingress configuration using `kubectl create -f <ingress-filename>`

Run the application with IP address of ingress