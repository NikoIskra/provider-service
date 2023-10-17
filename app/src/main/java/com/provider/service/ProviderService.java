package com.provider.service;

import java.util.UUID;

import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderReturnModel;
import com.provider.model.ProviderUpdateRequestModel;

public interface ProviderService {
    ProviderReturnModel save (UUID accountID, ProviderRequestModel providerRequestModel);
    ProviderReturnModel patch (UUID accountID, Long providerID, ProviderUpdateRequestModel providerRequestModel);
}
