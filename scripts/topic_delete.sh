#!/bin/bash

if [ $# -ne 1 ]
then
    echo "Missing argument"
    exit 0
else 
    docker exec broker kafka-topics --create --topic $1 --bootstrap-server broker:9092
fi
