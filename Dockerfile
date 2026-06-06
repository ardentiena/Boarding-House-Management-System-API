# Stage 1: Build the JAR using Maven and OpenJDK 25
FROM maven:3.9-eclipse-temurin-25-alpine AS build
WORKDIR /app

# Copy pom.xml and cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build the package
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime environment using JRE 25
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]