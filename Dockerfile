
FROM amazoncorretto:17.0.4-alpine3.16 as builder

# Install Maven and other required tools
RUN apk update && apk add maven

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]