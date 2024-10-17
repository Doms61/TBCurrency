# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the generated JAR file from the build context
COPY target/TBKurz-0.0.1-SNAPSHOT.jar /app/TBKurz-0.0.1-SNAPSHOT.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/TBKurz-0.0.1-SNAPSHOT.jar"]
