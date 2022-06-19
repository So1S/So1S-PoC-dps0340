envsubst < job.template.yaml > job.yaml
skaffold config set --global local-cluster true
eval $(minikube -p custom docker-env)
kubectl delete -f job.yaml
skaffold run --tail