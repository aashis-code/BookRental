#FROM openjdk:17
#ARG JAR_FILE=./target/*.jar
#RUN mkdir ./upload
#COPY ${JAR_FILE} app.jar
#
#ENV JAVA_OPTS="-Xmx1g -Xms256m"
#
#ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]

# Use OpenJDK 17 as the base image
#FROM openjdk:17
FROM eclipse-temurin:17-jdk


# Set the working directory inside the container
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven && apt-get clean

# Copy the Maven project configuration files
COPY pom.xml /app

# Download and cache dependencies
RUN mvn dependency:go-offline -B

# Copy the source code into the container
COPY src /app/src

# Build the Spring Boot application
RUN mvn clean package -DskipTests

# Expose the port on which the Spring Boot app runs
EXPOSE 8080

# Set JVM options
ENV JAVA_OPTS="-Xmx1g -Xms256m"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar target/*.jar"]
