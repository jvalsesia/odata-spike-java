# Description
[![build](https://github.com/subash-m/Spring-Boot-Olingo-OData-V4.01/actions/workflows/maven.yml/badge.svg)](https://github.com/subash-m/Spring-Boot-Olingo-OData-V4.01/actions/workflows/maven.yml)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE.txt)
![GitHub last commit](https://img.shields.io/github/last-commit/subash-m/Spring-Boot-Olingo-OData-V4.01)

Sample project to show integration of Spring Boot with Apache Olingo to create OData V4.01 API

## Prerequisites

* maven
* Java JDK 1.8

## To run the project

Execute the following command from the project folder

```maven
mvn spring-boot:run
```

## To check the results

Open browser and try the following URLs,

### Service Document

```URL
http://localhost:3000/V4.0/
```

### Metadata Document

```
http://localhost:3000/V4.0/$metadata
```

### Query / EntitySet

```
http://localhost:3000/V4.0/CustomerInfos?$format=JSON
```


# Database
# Project Info

## Start local environment with YUGABYTE using Docker Compose
```sh
docker compose up -d
```
- Check docker-compose.yaml

Starting database:
```sh
docker compose --file 'docker-compose.yaml' --project-name 'brix-hcs-survey' start 
```

## Yugabyte
1. Test the APIs
Clients can now connect to the YSQL API at localhost:5433 and the YCQL API at localhost:9042. The yb-master admin service is available at http://localhost:7000.


2. Connect to YCQL
```bash
docker exec -it yb-tserver-n1 /home/yugabyte/bin/ycqlsh yb-tserver-n1
```


3. Connect to the YSQL
```bash
docker exec -it yb-tserver-n1 /home/yugabyte/bin/ysqlsh -h yb-tserver-n1
```

4. Create Tables
```bash
\i create_yugabyte_database.sql
```


* List all the tables in the database that were created by docker-compose
```bash
\dt 
```

* Check the agent table.
```bash
SELECT * FROM public.agent;
```


# Azure

https://code.visualstudio.com/docs/java/java-spring-apps#_create-an-app-on-azure-spring-apps

https://learn.microsoft.com/en-us/azure/spring-apps/quickstart?tabs=Azure-portal%2CAzure-CLI%2CConsumption-workload&pivots=sc-enterprise



# Yugabyte Helm

0. kind create cluster --config kind-config.yaml


helm repo add yugabytedb https://charts.yugabyte.com
helm repo update

helm search repo yugabytedb/yugabyte --version 2.19.0

kubectl create namespace yb-survey
kubectl config set-context --current --namespace=yb-survey

helm install yb-survey yugabytedb/yugabyte \
--version 2.19.0 \
--set resource.master.requests.cpu=0.5,resource.master.requests.memory=0.5Gi,\
resource.tserver.requests.cpu=0.5,resource.tserver.requests.memory=0.5Gi,\
replicas.master=1,replicas.tserver=1 --namespace yb-survey

Because load balancers are not available in a Minikube environment, the LoadBalancers for yb-master-ui and yb-tserver-service remain in pending state. To disable these services, you can pass the enableLoadBalancer=False flag, as follows:

helm install yb-survey yugabytedb/yugabyte \
--version 2.19.0 \
--set resource.master.requests.cpu=0.5,resource.master.requests.memory=0.5Gi,\
resource.tserver.requests.cpu=0.5,resource.tserver.requests.memory=0.5Gi,\
replicas.master=1,replicas.tserver=1,enableLoadBalancer=False --namespace yb-survey

NAME: yb-survey
LAST DEPLOYED: Mon Sep 18 15:34:44 2023
NAMESPACE: yb-survey
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
1. Get YugabyteDB Pods by running this command:
  kubectl --namespace yb-survey get pods

2. Get list of YugabyteDB services that are running:
  kubectl --namespace yb-survey get services

3. Get information about the load balancer services:
  kubectl get svc --namespace yb-survey

4. Connect to one of the tablet server:
  kubectl exec --namespace yb-survey -it yb-tserver-0 bash

4.5. To run sql script
kubectl exec --namespace yb-survey -it yb-tserver-0 /bin/bash

5. Run YSQL shell from inside of a tablet server:
  kubectl exec --namespace yb-survey -it yb-tserver-0 -- /home/yugabyte/bin/ysqlsh -h yb-tserver-0.yb-tservers.yb-survey

6. Cleanup YugabyteDB Pods
  For helm 2:
  helm delete yb-survey --purge
  For helm 3:
  helm delete yb-survey -n yb-survey
  NOTE: You need to manually delete the persistent volume
  kubectl delete pvc --namespace yb-survey -l app=yb-master
  kubectl delete pvc --namespace yb-survey -l app=yb-tserver
[jcvalsesia@fedora ~]$ 


7. 
kubectl port-forward services/yb-tservers 9000:9000 12000:12000 11000:11000 13000:13000 9100:9100 6379:6379 9042:9042 5433:5433 -n yb-survey

8. 
kubectl --namespace yb-survey port-forward svc/yb-master-ui 7000:7000

9. Add yb-tserver-0 to /etc/hosts as:
127.0.0.1 yb-tservers



