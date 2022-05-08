FROM maven as build                         
COPY . .
ARG DATABASE_HOST_IP=172.17.0.2
RUN mvn -B clean package

FROM openjdk:17-jdk-alpine
ENV DATABASE_HOST_IP=localhost
ARG JAR_FILE=target/*.jar
COPY --from=build ${JAR_FILE} backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/backend.jar"]