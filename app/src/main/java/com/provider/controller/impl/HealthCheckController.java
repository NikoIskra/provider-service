package com.provider.controller.impl;

import com.provider.controller.HealthcheckApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController implements HealthcheckApi {
  @Override
  public ResponseEntity<Void> healthcheck() throws Exception {
    return ResponseEntity.ok().build();
  }
}
