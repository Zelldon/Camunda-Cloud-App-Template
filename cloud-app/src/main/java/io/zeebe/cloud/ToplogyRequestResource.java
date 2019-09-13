package io.zeebe.cloud;

import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloud/")
public class ToplogyRequestResource {

  @Autowired
  private ZeebeClient zeebeClient;

  @GetMapping("/hello")
  public String helloCloud() {
    return zeebeClient.newTopologyRequest().send().join().toString();
  }

}
