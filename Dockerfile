# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Java application (e.g., .class or .jar files) into the container
COPY ./src /app/src
COPY ./bin /app/bin

# Optional: Set environment variables (e.g., for runtime configs)
ENV JAVA_OPTS="-Xmx512m"

# Compile the Java program inside the container (if necessary)
RUN javac src/*.java -d bin

# Specify the default command to run the Java application
CMD ["java", "-cp", "bin", "Main"]
