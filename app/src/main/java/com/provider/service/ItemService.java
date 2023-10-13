package com.provider.service;

import java.util.UUID;

import com.provider.model.ItemRequestModel;
import com.provider.model.ItemReturnModel;

public interface ItemService {
    ItemReturnModel save(UUID accountID, Long providerID, ItemRequestModel itemRequestModel);
}
