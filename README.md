# k8s4j
Extend kubernetes functionality using java

## build and run

1. cleanup current `~/.kube` configs:
   ```bash
   rm -rf ~/.kube
   ```
1. spin up new cluster using tool you like:
   * enable Kubernetes cluster if you on mac or windows
   * k3s in k3d
   * minishift
   * minikube
   * kind
   * ...
1. show pods:
   ```bash
   ./mvnw ; java -jar ./target/*-all.jar
   ```

## resources

* https://medium.com/programming-kubernetes/building-stuff-with-the-kubernetes-api-part-2-using-java-ceb8a5ff7920
* https://www.youtube.com/watch?v=GDUBApV9mwA
* https://cloud.spring.io/spring-cloud-kubernetes/reference/html/
