all: clean

clean:
	gradle clean

compile:
	gradle build 
	
jar: compile
	gradle bootJar

run: jar
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/spring-mvc-1.0.jar

# Docker

docker-build: jar
	docker build -t spring-mvc .
	docker images

docker-run: docker-build
	docker run --name spring-mvc -td -p 80:8080 spring-mvc	
	docker ps

docker-clean:
	docker stop spring-mvc
	docker rm spring-mvc
	docker rmi spring-mvc

docker-shell:
	docker exec -it spring-mvc bash 

docker-push:
	docker login
	docker build -t $(account)/spring-mvc:latest -t $(account)/spring-mvc:latest .
	docker push $(account)/spring-mvc:latest 

# Pod

pod-run:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods spring-mvc

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it spring-mvc -- /bin/bash

pod-logs:
	kubectl logs -f spring-mvc

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service spring-mvc -o wide

service-delete:
	kubectl delete service spring-mvc