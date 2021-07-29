#!/bin/bash

docker exec -it broker kafka-console-producer --topic group-create-topic --bootstrap-server localhost:9092
