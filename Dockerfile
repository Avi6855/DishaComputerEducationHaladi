From openjdk:17-jdk-alpine

WORKDIR /app

COPY target/DishaComputerEducationHaladi-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]