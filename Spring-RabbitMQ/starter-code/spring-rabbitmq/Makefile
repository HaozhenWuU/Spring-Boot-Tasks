all: clean

clean:
	gradle clean

compile:
	gradle build

test:
	gradle test

build:
	gradle build

jar: 
	gradle bootJar

run:
	gradle bootRun
	 

# Docker

docker-build: jar
	docker build -t spring-rabbitmq . 
	docker images

docker-run: docker-build
	docker run --network springdemo --name spring-rabbitmq -td -p 80:8080 spring-rabbitmq

docker-shell: 
	docker exec -it spring-rabbitmq bash 

docker-clean:
	docker stop springdemo
	docker rm springdemo

docker-push:
	docker login
	docker build -t $(account)/spring-rabbitmq:v1 -t $(account)/spring-rabbitmq:v1 .
	docker push $(account)/spring-rabbitmq:v1 

# Compose

network-ls:
	docker network ls 

network-create:
	docker network create --driver bridge springdemo

network-prune:
	docker network prune

shell:
	docker exec -it rabbitmq bash

network-inspect:
	docker network inspect springdemo


rabbit:
	docker run --name rabbitmq \
			   --network springdemo \
			   -p 8080:15672 -p 4369:4369 -p 5672:5672 \
			   -d rabbitmq:3-management	

console: 
	open http://localhost:8080


# Pod

pod-run:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods spring-gumball

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it spring-gumball -- /bin/bash

pod-logs:
	kubectl logs -f spring-gumball

# Deployment

deployment-create:
	kubectl create -f deployment.yaml --save-config 

deployment-get:
	kubectl get deployments

deployment-get-pods:
	kubectl get pods -l name=spring-gumball

deployment-pod-shell:
	kubectl exec -it $(pod) -- /bin/bash

deployment-upgrade:
	kubectl apply  -f deployment.yaml

deployment-delete:
	kubectl delete deployment spring-gumball-deployment

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service spring-gumball -o wide

service-delete:
	kubectl delete service spring-gumball

# Ingress

ingress-apply:
	kubectl apply -f ingress.yaml

ingress-ip:
	kubectl get ingress spring-gumball-ingress




