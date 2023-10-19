package com.provider.controller.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.provider.controller.ItemApi;
import com.provider.model.ItemRequestModel;
import com.provider.model.ItemReturnModel;
import com.provider.model.ItemUpdateRequestModel;
import com.provider.model.ItemUpdateReturnModel;
import com.provider.service.ItemService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
public class ItemController implements ItemApi {

    @Autowired
    private ItemService itemService;

    @Override
    public ResponseEntity<ItemReturnModel> apiV1ProviderProviderIdItemPost(@NotNull UUID X_ACCOUNT_ID, Long providerId,
            @Valid ItemRequestModel itemRequestModel) throws Exception {
        ItemReturnModel itemReturnModel = itemService.save(X_ACCOUNT_ID, providerId, itemRequestModel);
                HttpHeaders returnHeaders = new HttpHeaders();
        String headerString = "/api/v1/provider/" + providerId.toString() + "/item/" + itemReturnModel.getResult().getId().toString();
        returnHeaders.set("Location", headerString);
        return ResponseEntity.status(HttpStatus.CREATED).headers(returnHeaders).body(itemReturnModel);
    }

    @Override
    public ResponseEntity<ItemUpdateReturnModel> apiV1ProviderProviderIdItemItemIdPut(@NotNull UUID X_ACCOUNT_ID,
            Long providerId, Long itemId, @Valid ItemUpdateRequestModel itemUpdateRequestModel) throws Exception {
                ItemUpdateReturnModel itemUpdateReturnModel = itemService.put(X_ACCOUNT_ID, providerId, itemId, itemUpdateRequestModel);
                return ResponseEntity.status(HttpStatus.OK).body(itemUpdateReturnModel);
            }

}
