#!/bin/bash

docker run  --name mysqlteam1 -v /root/mysqlDocker:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=root -d dbserver:1.0.0 /bin/bash
docker pull smageswaran/broadleaf:latest
docker run -it smageswaran/broadleaf:latest --link mysqlteam1 /bin/bash
