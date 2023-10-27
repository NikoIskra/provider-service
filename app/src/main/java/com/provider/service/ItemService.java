package com.provider.service;

import com.provider.model.ItemGetReturnModel;
import com.provider.model.ItemPostRequestModel;
import com.provider.model.ItemPostReturnModel;
import com.provider.model.ItemUpdateRequestModel;
import com.provider.model.ItemUpdateReturnModel;
import java.util.UUID;

public interface ItemService {
  ItemPostReturnModel save(UUID accountID, Long providerID, ItemPostRequestModel itemRequestModel);

  ItemUpdateReturnModel put(
      UUID accountID, Long ProviderID, Long itemID, ItemUpdateRequestModel itemUpdateRequestModel);

  ItemGetReturnModel get(UUID accountID, Long ProviderID, Long itemID);
}
