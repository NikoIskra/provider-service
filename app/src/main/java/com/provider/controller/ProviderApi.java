/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.6.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.provider.controller;

import com.provider.model.ErrorResponse;
import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderReturnModel;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Validated
public interface ProviderApi {

    /**
     * POST /api/v1/provider
     * Insert provider
     *
     * @param X_ACCOUNT_ID  (required)
     * @param providerRequestModel Provider to be added (required)
     * @return Created (status code 201)
     *         or Bad request! (status code 400)
     *         or Conflict! (status code 409)
     */
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/api/v1/provider",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<ProviderReturnModel> apiV1ProviderPost(
        @NotNull  @RequestHeader(value = "X-ACCOUNT-ID", required = true) UUID X_ACCOUNT_ID,
         @Valid @RequestBody ProviderRequestModel providerRequestModel
    ) throws Exception;

}