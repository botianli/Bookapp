# FROM maven:3.5.2-jdk-8-alpine
# COPY pom.xml /searchservice
# COPY src /searchservice/src
# WORKDIR /searchservice/
# RUN mvn clean package -DskipTests

FROM openjdk:11-jdk
ADD target/search-service.jar search-service.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar", "search-service.jar"]
