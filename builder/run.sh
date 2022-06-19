#!/usr/bin/env bash

cd /usr/src/git-repo/builder

kubectl delete -f deployment.yaml --wait

cd /usr/src/build-repo
./dockerize.sh

cd /usr/src/git-repo/builder

kubectl apply -f deployment.yaml --wait