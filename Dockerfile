FROM openjdk:13-alpine
ARG JAR_FILE=build/libs/kafka-consumer-application-standalone-0.0.1.jar
ARG PROPERTY_FILE=src/main/resources/dev.properties
COPY ${JAR_FILE} app.jar
COPY ${PROPERTY_FILE} dev.properties
ENTRYPOINT ["java","-jar","/app.jar", "dev.properties"]

# docker build --build-arg JAR_FILE=build/libs/\*.jar -t mcc/kafkaconsumer .
# docker run --name kafkaconsumer --restart=unless-stopped --net mcc-network mcc/kafkaconsumer