all: clean

clean:
	gradle clean

compile:
	gradle build 

jar: compile
	gradle bootJar

run: jar
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/tacos-cloud-v2-1.0.jar

# Docker

docker-build: jar
	docker build -t tacos-cloud-v2 .
	docker images

docker-run: docker-build
	docker run --name tacos-cloud-v2 -td -p 80:8080 tacos-cloud-v2	
	docker ps

docker-clean:
	docker stop tacos-cloud-v2
	docker rm tacos-cloud-v2
	docker rmi tacos-cloud-v2

docker-shell:
	docker exec -it tacos-cloud-v2 bash 

docker-push:
	docker login
	docker build -t $(account)/tacos-cloud-v2:latest -t $(account)/tacos-cloud-v2:latest .
	docker push $(account)/tacos-cloud-v2:latest 

# Pod

pod-run:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods tacos-cloud-v2

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it tacos-cloud-v2 -- /bin/bash

pod-logs:
	kubectl logs -f tacos-cloud-v2

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service tacos-cloud-v2 -o wide

service-delete:
	kubectl delete service tacos-cloud-v2