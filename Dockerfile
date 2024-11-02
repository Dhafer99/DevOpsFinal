FROM openjdk:17-jdk-alpine
EXPOSE 8082
ADD target/devopsproject-1.0.jar devopsproject-1.0.jar
ENTRYPOINT ["java","-jar","/devopsproject-1.0.jar"]