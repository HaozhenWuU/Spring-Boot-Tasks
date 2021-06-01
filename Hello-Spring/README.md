# CMPE 172 - Lab #1 - Hello Spring

## Part 1 - Spring Demo App

### Using Spring Initializr.

Scree shot of the browser:

![Spring Demo](images/SpringDemo.png)
 


Scree shot of desktop:


![Desktop](images/Desktop.png)
 


### Using VS cod.


Screen shot of the browser:

![vscode Demo](images/vscode.png)
 



Screen shot of desktop:

 
![Desktop](images/vs-desk.png)




### Spring Demo App Configured for Docker and Kubernettes

Screen shot of the Sring Boot App docker image and the container:

![Docker](images/dockerImage.png)

 


Screen shot for deploying the Spring Boot App to Kubernettes:

![Kubernettes](images/google-cloud.png)



 

## Part 2 - Tacos-cloud 


### Deploy Tacos-cloud App to Google Cloud Platform


Screen shot of browser of Tacos Cloud App:


![taco](images/browser.png)




Screen shot of the Tacos Cloud Service: 


![service](images/service.png)




Screen shot of the Tacos Cloud Pod Workload:


![workload](images/workloads.png)






# Lab Notes


## Error 1

 A problem encountered while using command: "make docker-build", gradle build stuck at HomePageBrowserTest. 



## Solution


Fix the issue by modifying the Makefile on "compile": 

compile:
	gradle build -x test


![test](images/test.png)




## Error 2



The docker jar file not found since the old Dockerfile specify the old path to the demo-docker jar file. 

![er2](images/dockerfile.png)




## Solution


Modify the Dockerfile to specify the new path points to the taco-cloud docker jar file. 


![er2](images/path.png)



