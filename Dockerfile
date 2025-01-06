# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the source and binary directories into the container
COPY ["Capstone 2 Refactoring/src", "/app/src"]
COPY ["Capstone 2 Refactoring/bin", "/app/bin"]

# Optional: Set environment variables (e.g., for runtime configs)
ENV JAVA_OPTS="-Xmx512m"

# Compile the Java program inside the container
RUN javac $(find /app/src -name "*.java") -d /app/bin

# Specify the default command to run the Java application
CMD ["java", "-cp", "/app/bin", "QuickFoodApp"]
