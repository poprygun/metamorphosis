#!/usr/bin/env bash

echo "Containers to be deleted"
docker ps -a | awk '{ print $1,$2 }' | grep wurstmeister


docker ps -a | awk '{ print $1,$2 }' | grep wurstmeister | awk '{print $1 }' | xargs -I {} docker rm {}