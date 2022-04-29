FROM openjdk:17-jdk-alpine
ENV DATABASE_HOST_IP=localhost
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} backend.jar
ENTRYPOINT ["java","-jar","/backend.jar"]