# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Copy the images into the container
COPY Logo.png upi.png /

# Set the working directory to /app
WORKDIR /app

# Create paid and unpaid directories
RUN mkdir -p paid unpaid

# Copy the fat jar into the container at /app
COPY /target/bill.jar /target/config.properties /app

# Make port 8083 available to the world outside this container
EXPOSE 8083

# Run jar file when the container launches
CMD ["java", "-jar", "bill.jar"]
