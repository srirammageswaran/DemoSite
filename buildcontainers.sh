#!/bin/bash


mvn clean 
mvn install
rm -rf Docker/mycompany.war
cp ./site/target/mycompany.war ./Docker/mycompany.war
cd Docker
docker build -t broadleaf:4.0.0 .
docker tag -f broadleaf:4.0.0 smageswaran/broadleaf:4.0.0 
docker tag -f broadleaf:4.0.0 smageswaran/broadleaf:latest
docker push smageswaran/broadleaf:4.0.0 
docker push smageswaran/broadleaf:latest
