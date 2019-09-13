.PHONE: all
all: build push deploy

.PHONY: build
build: 
	docker build . -t gcr.io/<YOUR_NAMESPACE>/<YOUR_PROJECT>:<VERSION>

.PHONY: push
push: 
	docker push gcr.io/<YOUR_NAMESPACE>/<YOUR_PROJECT>:<VERSION>

.PHONY: deploy
deploy:
	kubectl delete -f k8s/cloud-app.yml
	kubectl apply -f k8s/cloud-app.yml
