package com.provider.service.impl;

import com.provider.model.ProviderGetAllReturnModel;
import com.provider.model.ProviderGetAllReturnModelResult;
import com.provider.model.ProviderGetDataObject;
import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderReturnModel;
import com.provider.model.ProviderUpdateRequestModel;
import com.provider.model.StatusEnum;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.repository.ProviderRepository;
import com.provider.service.EntityConverterService;
import com.provider.service.ModelPopulationService;
import com.provider.service.ProviderService;
import com.provider.service.ProviderValidator;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

  private final ProviderRepository providerRepository;

  private final ProviderValidator providerValidator;

  private final EntityConverterService entityConverter;

  private final ModelPopulationService modelPopulationService;

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

  @Override
  public ProviderGetAllReturnModel getAll(UUID accountID, int pageNo, int pageSize) {
    providerValidator.validateProviderGetRequest(accountID);
    List<StatusEnum> statusList = new ArrayList<>();
    for (StatusEnum status : StatusEnum.values()) {
      if (!status.equals(StatusEnum.CANCELLED)) {
        statusList.add(status);
      }
    }
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt"));
    Page<Provider> providersPage = providerRepository.findAllByStatusIn(pageable, statusList);
    List<Provider> providers = providersPage.getContent();
    List<ProviderGetDataObject> providerGetDataObjects = new ArrayList<>();
    modelPopulationService.populateProviderGetDataObject(
        providerGetDataObjects, providers, statusList);
    ProviderGetAllReturnModelResult providerGetAllReturnModelResult =
        new ProviderGetAllReturnModelResult()
            .data(providerGetDataObjects)
            .page(pageNo)
            .pageSize(pageSize)
            .numberOfPages(providersPage.getTotalPages());
    return new ProviderGetAllReturnModel().ok(true).result(providerGetAllReturnModelResult);
  }
}
