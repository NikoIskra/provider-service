package com.provider.controller.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.provider.controller.ProviderApi;
import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderReturnModel;
import com.provider.service.ProviderService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
public class ProviderController implements ProviderApi {

    @Autowired
    private ProviderService providerService;

    @Override
    public ResponseEntity<ProviderReturnModel> apiV1ProviderPost(@NotNull UUID X_ACCOUNT_ID,
            @Valid ProviderRequestModel providerRequestModel) throws Exception {
        ProviderReturnModel providerReturnModel = providerService.save(X_ACCOUNT_ID, providerRequestModel);
        HttpHeaders returnHeaders = new HttpHeaders();
        String headerString = "/api/v1/provider/" + providerReturnModel.getResult().getId().toString();
        returnHeaders.set("Location", headerString);
        return ResponseEntity.status(HttpStatus.CREATED).headers(returnHeaders).body(providerReturnModel);
    }
    
}