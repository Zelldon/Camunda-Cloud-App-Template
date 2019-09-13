package io.zeebe.cloud;

import io.zeebe.client.ZeebeClient;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudApplication {

  private static final Logger LOG = LoggerFactory.getLogger(CloudApplication.class);

  @Autowired private ZeebeClient zeebeClient;

  public static void main(String... args) {
    SpringApplication.run(CloudApplication.class, args);
  }

  @PostConstruct
  public void init() {
    LOG.info("Checking connection to Zeebe...");
    zeebeClient.newTopologyRequest().send().join();
    LOG.info("Connected.");

    LOG.info("Launch Zeebe workers");

    // do you stuff

    LOG.info("Ready!");
  }

}
