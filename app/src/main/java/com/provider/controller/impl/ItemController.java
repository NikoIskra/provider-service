package com.provider.controller.impl;

import com.provider.controller.ItemApi;
import com.provider.model.ItemGetReturnModel;
import com.provider.model.ItemPostRequestModel;
import com.provider.model.ItemPostReturnModel;
import com.provider.model.ItemUpdateRequestModel;
import com.provider.model.ItemUpdateReturnModel;
import com.provider.service.ItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController implements ItemApi {

  @Autowired private ItemService itemService;

  @Override
  public ResponseEntity<ItemPostReturnModel> addItem(
      @NotNull UUID X_ACCOUNT_ID, Long providerId, @Valid ItemPostRequestModel itemRequestModel)
      throws Exception {
    ItemPostReturnModel itemReturnModel =
        itemService.save(X_ACCOUNT_ID, providerId, itemRequestModel);
    HttpHeaders returnHeaders = new HttpHeaders();
    String headerString =
        "/api/v1/provider/"
            + providerId.toString()
            + "/item/"
            + itemReturnModel.getResult().getId().toString();
    returnHeaders.set("Location", headerString);
    return ResponseEntity.status(HttpStatus.CREATED).headers(returnHeaders).body(itemReturnModel);
  }

  @Override
  public ResponseEntity<ItemUpdateReturnModel> updateItem(
      @NotNull UUID X_ACCOUNT_ID,
      Long providerId,
      Long itemId,
      @Valid ItemUpdateRequestModel itemUpdateRequestModel)
      throws Exception {
    ItemUpdateReturnModel itemUpdateReturnModel =
        itemService.put(X_ACCOUNT_ID, providerId, itemId, itemUpdateRequestModel);
    return ResponseEntity.status(HttpStatus.OK).body(itemUpdateReturnModel);
  }

  @Override
  public ResponseEntity<ItemGetReturnModel> getItemByID(
      @NotNull UUID X_ACCOUNT_ID, Long providerId, Long itemId) throws Exception {
    ItemGetReturnModel itemGetReturnModel = itemService.get(X_ACCOUNT_ID, providerId, itemId);
    return ResponseEntity.status(HttpStatus.OK).body(itemGetReturnModel);
  }
}
