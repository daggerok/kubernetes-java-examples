package daggerok;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import io.vavr.control.Try;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * ./mvnw clean compile exec:java -Dexec.mainClass=daggerok.Main
 * or:
 * ./mvnw ; java -jar ./target/*-all.jar
 */
@Slf4j
public class Main {

  @Value
  static class Info {
    final String name, namespace, resourceVersion;

    static Info fromV1ObjectMeta(V1ObjectMeta metadata) {
      return new Info(metadata.getName(),
                      metadata.getNamespace(),
                      metadata.getResourceVersion());
    }
  }

  static Function<Throwable, RuntimeException> reThrow = RuntimeException::new;
  static Consumer<Throwable> onError = e -> log.error("oops-{}: {}", System.nanoTime(), e.getLocalizedMessage());

  public static void main(String... args) {
    ApiClient client = Try.of(Config::defaultClient)
                          .onFailure(onError)
                          // .getOrElseThrow(reThrow);
                          .getOrNull();
    if (Objects.isNull(client)) return;
    Configuration.setDefaultApiClient(client);
    CoreV1Api api = new CoreV1Api();
    V1PodList list = Try.of(() -> api.listPodForAllNamespaces(null,
                                                              null,
                                                              null,
                                                              null,
                                                              null,
                                                              null,
                                                              null,
                                                              null,
                                                              null))
                        .onFailure(onError)
                        // .getOrElseThrow(reThrow);
                        .getOrNull();
    if (Objects.isNull(list)) return;
    list.getItems()
        .stream()
        .map(V1Pod::getMetadata)
        .filter(Objects::nonNull)
        .map(Info::fromV1ObjectMeta)
        .forEach(info -> log.info("found: {}", info));
  }
}
