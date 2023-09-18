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

