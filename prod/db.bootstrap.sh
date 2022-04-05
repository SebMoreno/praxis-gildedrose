#!/usr/bin/env bash

DOCKER_TMP_DIR=/tmp/get-docker.sh

apt-get update

apt-get purge -y docker-ce docker-ce-cli containerd.io docker-ce-rootless-extras docker-scan-plugin
rm -rf /var/lib/docker /var/lib/containerd /etc/docker/ /var/run/docker*
curl -fsSL https://get.docker.com -o $DOCKER_TMP_DIR
sh $DOCKER_TMP_DIR || sh $DOCKER_TMP_DIR
rm -f $DOCKER_TMP_DIR

docker run --name my-postgres -e POSTGRES_PASSWORD=secret -p 5433:5432 -d postgres
docker update --restart always my-postgres