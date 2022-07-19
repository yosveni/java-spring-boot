FROM openjdk:16.0.2-oracle as build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN ./gradlew bootJar

FROM 728613379780.dkr.ecr.sa-east-1.amazonaws.com/linkapital-api-base:latest
COPY --from=build /project/platform/build/libs/com.linkapital.platform-0.0.1-SNAPSHOT.jar /app/com.linkapital.platform-0.0.1-SNAPSHOT.jar
CMD "java" "-jar" "com.linkapital.platform-0.0.1-SNAPSHOT.jar"