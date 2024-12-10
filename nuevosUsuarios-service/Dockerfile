FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} nuevosUsuarios-service.jar
ENTRYPOINT ["java", "-jar", "/nuevosUsuarios-service.jar"]