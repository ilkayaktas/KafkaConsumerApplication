#!/bin/bash

./gradlew build

containerId=$(docker stop kafkaconsumer)

# Remove container
docker rm $containerId

# Remove image
docker rmi ilkayaktas/kafkaconsumer

# Build image
docker build --build-arg JAR_FILE=build/libs/\*.jar -t ilkayaktas/kafkaconsumer .


# Run container
docker run --name kafkaconsumer --restart=unless-stopped --net mcc-network ilkayaktas/kafkaconsumer
