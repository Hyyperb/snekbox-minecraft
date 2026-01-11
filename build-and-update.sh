#!/bin/bash

mvn clean
mvn package

cp target/PythonRunner-1.0-SNAPSHOT.jar minecraft-server/data/plugins/

cd minecraft-server || exit
sudo docker compose exec mc rcon-cli reload confirm
