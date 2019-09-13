.PHONE: all
all: build push deploy

projectNamespace=<NAMESPACE>
projectName=<APP>
projectVersion=latest

.PHONY: build
build: 
	docker build . -t gcr.io/$(projectNamespace)/$(projectName):$(projectVersion)

.PHONY: push
push: 
	docker push gcr.io/$(projectNamespace)/$(projectName):$(projectVersion)

.PHONY: deploy
deploy:
	kubectl delete -f k8s/cloud-app.yml
	kubectl apply -f k8s/cloud-app.yml
