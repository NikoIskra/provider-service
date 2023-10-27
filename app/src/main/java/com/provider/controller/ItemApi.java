/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.6.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.provider.controller;

import com.provider.model.ErrorResponse;
import com.provider.model.ItemGetReturnModel;
import com.provider.model.ItemPostRequestModel;
import com.provider.model.ItemPostReturnModel;
import com.provider.model.ItemUpdateRequestModel;
import com.provider.model.ItemUpdateReturnModel;
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
public interface ItemApi {

    /**
     * POST /api/v1/provider/{provider-id}/item
     * Add item
     *
     * @param X_ACCOUNT_ID  (required)
     * @param providerId  (required)
     * @param itemPostRequestModel Item to be added (required)
     * @return Created (status code 201)
     *         or Bad request! (status code 400)
     */
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/api/v1/provider/{provider-id}/item",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<ItemPostReturnModel> addItem(
        @NotNull  @RequestHeader(value = "X-ACCOUNT-ID", required = true) UUID X_ACCOUNT_ID,
         @PathVariable("provider-id") Long providerId,
         @Valid @RequestBody ItemPostRequestModel itemPostRequestModel
    ) throws Exception;


    /**
     * GET /api/v1/provider/{provider-id}/item/{item-id}
     * Get item
     *
     * @param X_ACCOUNT_ID  (required)
     * @param providerId  (required)
     * @param itemId  (required)
     * @return fetched (status code 200)
     *         or Not found! (status code 404)
     */
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/v1/provider/{provider-id}/item/{item-id}",
        produces = { "application/json" }
    )
    ResponseEntity<ItemGetReturnModel> getItemByID(
        @NotNull  @RequestHeader(value = "X-ACCOUNT-ID", required = true) UUID X_ACCOUNT_ID,
         @PathVariable("provider-id") Long providerId,
         @PathVariable("item-id") Long itemId
    ) throws Exception;


    /**
     * PUT /api/v1/provider/{provider-id}/item/{item-id}
     * Update item
     *
     * @param X_ACCOUNT_ID  (required)
     * @param providerId  (required)
     * @param itemId  (required)
     * @param itemUpdateRequestModel Item to be updated (required)
     * @return updated (status code 200)
     *         or Bad request! (status code 400)
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/api/v1/provider/{provider-id}/item/{item-id}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<ItemUpdateReturnModel> updateItem(
        @NotNull  @RequestHeader(value = "X-ACCOUNT-ID", required = true) UUID X_ACCOUNT_ID,
         @PathVariable("provider-id") Long providerId,
         @PathVariable("item-id") Long itemId,
         @Valid @RequestBody ItemUpdateRequestModel itemUpdateRequestModel
    ) throws Exception;

}
