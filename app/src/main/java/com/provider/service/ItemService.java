package com.provider.service;

import java.util.UUID;

import com.provider.model.ItemRequestModel;
import com.provider.model.ItemReturnModel;
import com.provider.model.ItemUpdateRequestModel;
import com.provider.model.ItemUpdateReturnModel;

public interface ItemService {
    ItemReturnModel save(UUID accountID, Long providerID, ItemRequestModel itemRequestModel);
    ItemUpdateReturnModel put(UUID accountID, Long ProviderID, Long itemID, ItemUpdateRequestModel itemUpdateRequestModel);
}
