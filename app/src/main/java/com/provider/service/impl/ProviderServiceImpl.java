package com.provider.service.impl;

import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderReturnModel;
import com.provider.model.ProviderUpdateRequestModel;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.repository.ProviderRepository;
import com.provider.service.EntityConverterService;
import com.provider.service.ProviderService;
import com.provider.service.ProviderValidator;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

  private final ProviderRepository providerRepository;

  private final ProviderValidator providerValidator;

  private final EntityConverterService entityConverter;

  @Override
  @Transactional
  public ProviderReturnModel save(UUID accountID, ProviderRequestModel providerRequestModel) {
    providerValidator.validateProviderRequest(accountID, providerRequestModel);
    Provider provider = entityConverter.convertProviderRequestModelToProvider(providerRequestModel);
    provider.setOwnerId(accountID);
    providerRepository.saveAndFlush(provider);
    return entityConverter.convertProviderToReturnModel(provider);
  }

  @Override
  public ProviderReturnModel patch(
      UUID accountID, Long providerID, ProviderUpdateRequestModel providerUpdateRequestModel) {
    providerValidator.validateProviderPatchRequest(
        accountID, providerID, providerUpdateRequestModel);
    Provider provider = providerRepository.getById(providerID);
    entityConverter.patchRequestModelToProvider(providerUpdateRequestModel, provider);
    providerRepository.save(provider);
    return entityConverter.convertProviderToReturnModel(provider);
  }
}
