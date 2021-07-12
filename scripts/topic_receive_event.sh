#!/bin/bash

if [ $# -ne 1 ]
then
    echo "Missing argument"
    exit 0
else 
    docker exec broker kafka-console-consumer --topic $1 --from-beginning --bootstrap-server localhost:9092
fi
