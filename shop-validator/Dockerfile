#### Stage 1: Build the application
FROM openjdk:8-jdk-alpine as BUILD
 
# Create a temp directory
VOLUME /tmp
 
# Set the current working directory inside the image
WORKDIR /app
 
RUN chmod 777 -R /app
 
# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn
 
# Copy the pom.xml file
COPY pom.xml .
 
# if the file has a line ending of windows format
# RUN apk add dos2unix
 
# change permission file mvnw
# RUN dos2unix mvnw
 
# Build all the dependencies in preparation to go offline. 
# This is a separate step so the dependencies will be cached unless 
# the pom.xml file has changed.
RUN chmod 777 mvnw
 
RUN ./mvnw dependency:go-offline -B
 
# Copy the project source
COPY src src
 
# Package the application
RUN ./mvnw package -DskipTests
 
#### Stage 2: A minimal docker image with command to run the app 
FROM openjdk:8-jre-alpine
 
# Copy project dependencies from the build stage
COPY --from=BUILD app/target/shop-validator-0.0.1.jar /app/
 
WORKDIR /app
 
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=hom", "shop-validator-0.0.1.jar"]