#!/bin/bash

sudo docker rm -f withpet-api
sudo docker build --tag kjuiop/withpet-api:0.0.1 .
sudo docker rmi $(sudo docker images | grep "<none>" | awk '{print $3}')
sudo docker push kjuiop/withpet-api:0.0.1
sudo docker images

#docker run -d --name withpet-api -p 80:8080  kjuiop/withpet-api:0.0.1
# docker exec -it withpet-api /bin/bash