FROM maven:3.8.4-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install -DskipTests

# Package stage
FROM openjdk:17
COPY --from=build /home/app/target/robinhood-1.jar robinhood-1.jar
EXPOSE 8080
ENTRYPOINT ["java","-Duser.timezone=GMT","-jar","robinhood-1.jar"]