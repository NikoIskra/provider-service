package com.provider.service;

import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderReturnModel;
import com.provider.model.ProviderUpdateRequestModel;
import java.util.UUID;

public interface ProviderService {
  ProviderReturnModel save(UUID accountID, ProviderRequestModel providerRequestModel);

  ProviderReturnModel patch(
      UUID accountID, Long providerID, ProviderUpdateRequestModel providerRequestModel);
}
