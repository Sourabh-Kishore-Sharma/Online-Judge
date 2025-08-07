FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR from the target folder
COPY target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
