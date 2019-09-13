# Camunda-Cloud-App-Template

This repository contains an template to create a Spring Boot Application which can be deployed on kubernetes and uses Camunda Cloud to execute BPMN Workflows.


If you want to see an real world example, just check out our BPMN Games Slack Bot https://github.com/saig0/bpmn-games
we presented on Camunda Con 2019.p

## Prerequisites

 * You need a Cloud Provider like AWS, GCloud or something similar to deploy your Spring Boot Application.
The example will use GCloud. It is also necessary to have container registry like gcr.io, where you can store your docker images.
   * You need gcloud (cli) and kubectl installed on your system
   * You need to be authenticated with your cloud provider
   * To get started with GCloud check out this quickstart guide https://cloud.google.com/functions/docs/quickstart
 * You need access to https://console.cloud.camunda.io
   * To get started with Camunda Cloud check out our blog https://zeebe.io/blog/2019/09/announcing-camunda-cloud/

## How to use

### Makefile

*Note:* You need a cloud provider

Fork this repository and adjust the Makefile.
The Makefile will use `gcr.io` you need to adjust this as well if you want to use a different provider.
```
projectNamespace=<NAMESPACE>
projectName=<APP>
projectVersion=latest
```

Make sure that you reference the same docker image in the `k8s/cloud-app.yml` file.
Search for `<IMAGE>` and replace it with your image tag.

### Cloud Credentials

*Note:* You need access to https://console.cloud.camunda.io

**Note: Make sure that you do not commit your secret in your repository!**

 * Create a new Zeebe cluster via the Camunda Cloud console.
 * Create a new Client in Camunda Cloud
 * Copy all credentials (like ClientId, ClientSecret and Zeebe Contactpoint) and put them in the `k8s/cloud-app-secret.yml` file
 * With `kubectl apply -f k8s/cloud-app-secret.yml` you can create the secret in your cluster.

### Setup Ingress

To use ingress you have to setup an ingress controller, like ngnix.
How to do that on gcloud check this guide https://cloud.google.com/community/tutorials/nginx-ingress-gke

After you have installed the ingress controller you can just create the ingress via `kubectl apply -f k8s/cloud-app-ingress.yml`

To see the status and the bind address use `kubectl get ingress` this will print something like that:
```
[zell Zeebe-Cloud-App-Template]$ kubectl get ingress
NAME                HOSTS   ADDRESS         PORTS   AGE
cloud-app-ingress   *       X.Y.Z.12         80      64m
```

### Deployment

After all the setup is done you should be able to run `make` and the cluster deployment should happen.

`make`:
  
  This will build the template project via maven and create an docker image with the given tag.
  After building the image it will push it to your specified docker registry (gcr.io for example).
  At the end it should be able to deploy the application as deployment on kubernetes and creates an services which exposes the container port such that ingress is able to forward the traffic to it.

`make build`:

  This will just build the template project and create the docker image with the given tag. It will also push the image to the given container registry.

`make deploy`:
  
  This will deploy the template docker image at kubernetes and create an service for it, which exposes the container port such that ingress is able to forward the traffic to it.

### Verify

To check if everything works, you can run `curl  X.Y.Z.12/cloud/hello`

This should print something like this:
```
TopologyImpl{brokers=[BrokerInfoImpl{nodeId=0, host='zeebe-0', port=26501, partitions=[PartitionInfoImpl{partitionId=2, role=LEADER}, PartitionInfoImpl{partitionId=1, role=LEADER}]}], clusterSize=1, partitionsCount=2, replicationFactor=1} 
```

With this `GET` request you get access to the Zeebe Cluster topology.
