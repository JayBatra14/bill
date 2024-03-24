# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory to /app
WORKDIR /app

# Copy the fat jar into the container at /app
COPY /target/api.jar /app

# Make port 8083 available to the world outside this container
EXPOSE 8083

# Run jar file when the container launches
CMD ["java", "-jar", "api.jar"]
