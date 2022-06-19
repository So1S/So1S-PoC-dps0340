#!/usr/bin/env bash

CURR_DIR=$(pwd)

kubectl delete -f deployment.yaml --wait

conda deactivate
conda deactivate
conda activate poc

cd ./So1S-PoC-bentoml
./dockerize.sh

kubectl apply -f deployment.yaml --wait