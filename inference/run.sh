#!/usr/bin/env bash

CURR_DIR=$(pwd)

kubectl delete -f deployment.yaml --wait

conda deactivate
conda activate poc

cd ../../So1s-PoC-bentoml
./dockerize.sh

cd $CURR_DIR

kubectl apply -f deployment.yaml --wait