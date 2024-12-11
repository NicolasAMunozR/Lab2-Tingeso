FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} solicitudCredito-service.jar
ENTRYPOINT ["java", "-jar", "/solicitudCredito-service.jar"]