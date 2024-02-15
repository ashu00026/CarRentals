#
# Build stage
#
FROM maven:3.8.1-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
# WORKDIR /home/app
RUN mvn -f /home/app/pom.xml clean package
# Execute the mavean command to build the .jar file accoring to given pom.xml file.
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/app/target/carRentals_docker.jar"]
#ENTRYPOINT : Execute command for run the .jar file.We can use CMD instead of ENTRYPOINT.If we use CMD we can provide arguments to image when build it.