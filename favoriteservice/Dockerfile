FROM openjdk:11
#RUN ./mvnw package -DskipTests
ADD target/favoriteservice*.jar favoriteservice*.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "favoriteservice*.jar"]
