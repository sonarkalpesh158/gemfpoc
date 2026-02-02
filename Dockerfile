# 1. Using an image that already has Java 17 installed
FROM eclipse-temurin:17-jdk-jammy

# 2. Setting the folder inside the container
WORKDIR /app

# 3. Copying the JAR file which built with Maven into the container
COPY target/*.jar app.jar

# 4. Exposing the port so we can access it
EXPOSE 8080

# 5. The command to start  Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]