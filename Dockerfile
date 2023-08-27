# Dockerfile example
FROM eclipse-temurin:17.0.8_7-jre-jammy
#FROM eclipse-temurin:17-jre-alpine
#ARG JAR_FILE=target/*.jar
COPY target/SpringProject3-0.0.1-SNAPSHOT.jar SpringProject3-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/SpringProject3-0.0.1-SNAPSHOT.jar"]
