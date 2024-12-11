FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} evaluacionCredito-service.jar
ENTRYPOINT ["java", "-jar", "/evaluacionCredito-service.jar"]