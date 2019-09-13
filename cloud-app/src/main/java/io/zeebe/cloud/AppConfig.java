package io.zeebe.cloud;

import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Autowired private ZeebeClient zeebeClient;

  // put your application configuration here

}
