#!/usr/bin/env bash

source ./setEnv.sh

echo "Kafka Host: " $DOCKER_KAFKA_HOST

docker-compose -f docker-compose.yml up -d