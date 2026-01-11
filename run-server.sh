mkdir -p minecraft-server/data/plugins

mvn package
cp target/PythonRunner-1.0-SNAPSHOT.jar minecraft-server/data/plugins/

echo starting minecraft server...
cd minecraft-server
sudo docker compose up
