FROM openjdk:17-jdk-alpine
EXPOSE 8082
ADD target/DevOps_Project-1.0.jar DevOps_Project-1.0.jar
ENTRYPOINT ["java","-jar","/devopsproject-1.0.jar"]