package com.provider.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.provider.controller.HealthcheckApi;

@RestController
public class HealthCheckController implements HealthcheckApi {
    @Override
    public ResponseEntity<Void> apiV1HealthcheckGet() throws Exception {
        return ResponseEntity.ok().build();
    }
}
