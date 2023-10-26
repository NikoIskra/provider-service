package com.provider.service;

import com.provider.model.ItemGetReturnModel;
import com.provider.model.ItemRequestModel;
import com.provider.model.ItemReturnModel;
import com.provider.model.ItemUpdateRequestModel;
import com.provider.model.ItemUpdateReturnModel;
import java.util.UUID;

public interface ItemService {
  ItemReturnModel save(UUID accountID, Long providerID, ItemRequestModel itemRequestModel);

  ItemUpdateReturnModel put(
      UUID accountID, Long ProviderID, Long itemID, ItemUpdateRequestModel itemUpdateRequestModel);

  ItemGetReturnModel get(UUID accountID, Long ProviderID, Long itemID);
}
