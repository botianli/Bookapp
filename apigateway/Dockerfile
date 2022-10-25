FROM openjdk:11
ADD target/apigateway*.jar apigateway*.jar
EXPOSE 8099
ENTRYPOINT ["java", "-jar", "apigateway*.jar"]
