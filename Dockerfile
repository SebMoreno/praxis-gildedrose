# BUILD
FROM maven as backend-build
COPY . .
ARG DATABASE_HOST_IP=172.17.0.2
RUN mvn -B clean package -Dmaven.test.skip

# DEPLOY
FROM openjdk:17-jdk-alpine
ENV DATABASE_HOST_IP=localhost
ARG JAR_FILE=target/*.jar
COPY --from=backend-build ${JAR_FILE} backend.jar
ENTRYPOINT ["java","-jar","/backend.jar"]
EXPOSE 8080