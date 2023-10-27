package com.provider.service;

import com.provider.model.ProviderGetAllReturnModel;
import com.provider.model.ProviderPostRequestModel;
import com.provider.model.ProviderPostReturnModel;
import com.provider.model.ProviderUpdateRequestModel;
import java.util.UUID;

public interface ProviderService {
  ProviderPostReturnModel save(UUID accountID, ProviderPostRequestModel providerRequestModel);

  ProviderPostReturnModel patch(
      UUID accountID, Long providerID, ProviderUpdateRequestModel providerRequestModel);

  ProviderGetAllReturnModel getAll(UUID accountID, Integer pageNo, Integer pageSize);
}
