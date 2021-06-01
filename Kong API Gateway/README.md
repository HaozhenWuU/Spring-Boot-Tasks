# CMPE 172 - Lab #8 - Kong API Gateway

## Deployment to GKE

* Workloads

![workload](images/workloads.png)

* Services 

![services](images/services.png)

* jumpbox

![jump1](images/jumpbox.png)

![jump2](images/jumpbox-work.png)

* Kong Gateway Set Up

![kong1](images/kg1.png)

![kong2](images/kg2.png)

## Requests and Responds through KONG using CURL

* Ping 

![ping](images/ping.png)

* Create a new card

![new](images/new-card.png)

* display one card

![one](images/one-card.png)

* display all cards 

![all](images/all-card.png)

## Challanges 

The only issue I have encountered is that the starbucks-api docker image is not running since I deployed the API with MySQL. There is a connection failure with the MySQL database even though the service is online. I fixed the issue by deploying the API using H2 database. Another issue is that the starbucks api I created in lab#6 is not complete, so I wouldn't be able to test the API throughly.

To deploy the Starbucks API with MySQL, the application properties need to be changed to be able to connect to the database, for instance, the user name and database name must be specified in the properties.  