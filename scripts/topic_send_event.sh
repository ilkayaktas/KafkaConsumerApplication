#!/bin/bash

if [ $# -ne 1 ]
then
    echo "Missing argument"
    exit 0
else 
    docker exec -it broker kafka-console-producer --topic $1 --bootstrap-server localhost:9092
fi
