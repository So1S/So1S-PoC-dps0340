#!/usr/bin/env bash

cd /usr/src/git-repo/inference

systemctl enable docker
systemctl start docker

kubectl delete -f deployment.yaml --wait

cd /usr/src/build-repo
./dockerize.sh

cd /usr/src/git-repo/inference

kubectl apply -f deployment.yaml --wait