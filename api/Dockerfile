FROM gradle:7.4.1-jdk17 as builder
COPY . /usr/src/java-code/
WORKDIR /usr/src/java-code
RUN gradle bootJar -i --stacktrace

FROM ubuntu:22.04
EXPOSE 8080
USER root
WORKDIR /usr/src/java-app
COPY --from=builder /usr/src/java-code/build/libs/*.jar ./app.jar

RUN apt install -y openjdk-17-jre python3.8 python3.8-pip docker-ce docker-ce-cli

ENTRYPOINT ["java", "-jar", "app.jar"]