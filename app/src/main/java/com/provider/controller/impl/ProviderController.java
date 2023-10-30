package com.provider.controller.impl;

import com.provider.controller.ProviderApi;
import com.provider.model.ProviderGetAllReturnModel;
import com.provider.model.ProviderPostRequestModel;
import com.provider.model.ProviderPostReturnModel;
import com.provider.model.ProviderUpdateRequestModel;
import com.provider.service.ProviderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController implements ProviderApi {

  @Autowired private ProviderService providerService;

  @Override
  public ResponseEntity<ProviderPostReturnModel> addProvider(
      @NotNull UUID X_ACCOUNT_ID, @Valid ProviderPostRequestModel providerRequestModel)
      throws Exception {
    ProviderPostReturnModel providerReturnModel =
        providerService.save(X_ACCOUNT_ID, providerRequestModel);
    HttpHeaders returnHeaders = new HttpHeaders();
    String headerString = "/api/v1/provider/" + providerReturnModel.getResult().getId().toString();
    returnHeaders.set("Location", headerString);
    return ResponseEntity.status(HttpStatus.CREATED)
        .headers(returnHeaders)
        .body(providerReturnModel);
  }

  @Override
  public ResponseEntity<ProviderPostReturnModel> patchProvider(
      @NotNull UUID X_ACCOUNT_ID,
      Long providerId,
      @Valid ProviderUpdateRequestModel providerUpdateRequestModel)
      throws Exception {
    ProviderPostReturnModel providerReturnModel =
        providerService.patch(X_ACCOUNT_ID, providerId, providerUpdateRequestModel);
    return ResponseEntity.status(HttpStatus.OK).body(providerReturnModel);
  }

  @Override
  public ResponseEntity<ProviderGetAllReturnModel> getAllProviders(
      @NotNull UUID X_ACCOUNT_ID,
      @NotNull @Valid Integer page,
      @Min(20) @Max(100) @Valid Integer pageSize)
      throws Exception {
    ProviderGetAllReturnModel providerGetAllReturnModel =
        providerService.getAll(X_ACCOUNT_ID, page, pageSize);
    return ResponseEntity.status(HttpStatus.OK).body(providerGetAllReturnModel);
  }
}
