package com.provider.service;

import java.util.UUID;

import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderReturnModel;

public interface ProviderService {
    ProviderReturnModel save (UUID accountID, ProviderRequestModel providerRequestModel);
}
