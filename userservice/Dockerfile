#base image
FROM openjdk:11
#RUN mvn clean package -DskipTests
ADD target/user-service.jar user-service.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "user-service.jar"]