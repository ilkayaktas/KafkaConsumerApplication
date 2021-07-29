#!/bin/bash

docker exec broker kafka-topics --create --topic group-create-topic --bootstrap-server broker:9092 --replication-factor 1 --partitions 1 --property parse.key=true --property key.separator=":"

# Replication factor defines the number of copies of a topic in a Kafka cluster.