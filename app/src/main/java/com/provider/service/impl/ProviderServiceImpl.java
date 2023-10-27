package com.provider.service.impl;

import com.provider.model.GetAllProvidersProviderModel;
import com.provider.model.ProviderGetAllReturnModel;
import com.provider.model.ProviderGetAllReturnModelResult;
import com.provider.model.ProviderPostRequestModel;
import com.provider.model.ProviderPostReturnModel;
import com.provider.model.ProviderUpdateRequestModel;
import com.provider.model.StatusEnum;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.repository.ProviderRepository;
import com.provider.service.EntityConverterService;
import com.provider.service.ProviderService;
import com.provider.service.ProviderValidator;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

  private final List<StatusEnum> statusList =
      List.of(StatusEnum.ACTIVE, StatusEnum.SUSPENDED, StatusEnum.VIEW_ONLY);

  @Value("${page.size.default}")
  private Integer defaultPageSize;

  @Override
  @Transactional
  public ProviderPostReturnModel save(UUID accountID, ProviderPostRequestModel providerRequestModel) {
    providerValidator.validateProviderRequest(accountID, providerRequestModel);
    Provider provider = entityConverter.convertProviderRequestModelToProvider(providerRequestModel);
    provider.setOwnerId(accountID);
    providerRepository.saveAndFlush(provider);
    return entityConverter.convertProviderToReturnModel(provider);
  }

  @Override
  public ProviderPostReturnModel patch(
      UUID accountID, Long providerID, ProviderUpdateRequestModel providerUpdateRequestModel) {
    providerValidator.validateProviderPatchRequest(
        accountID, providerID, providerUpdateRequestModel);
    Provider provider = providerRepository.getById(providerID);
    entityConverter.patchRequestModelToProvider(providerUpdateRequestModel, provider);
    providerRepository.save(provider);
    return entityConverter.convertProviderToReturnModel(provider);
  }

  @Override
  public ProviderGetAllReturnModel getAll(UUID accountID, Integer pageNo, Integer pageSize) {
    providerValidator.validateProviderGetRequest(accountID);
    Integer pageSizeActual = Objects.requireNonNullElse(pageSize, defaultPageSize);
    Pageable pageable = PageRequest.of(pageNo, pageSizeActual, Sort.by("createdAt"));
    Page<Provider> providersPage = providerRepository.findAllWithChildren(pageable);
    List<Provider> providers = providersPage.getContent();
    List<GetAllProvidersProviderModel> providerGetDataObjects = new ArrayList<>();
    entityConverter.convertProviderToGetDataObjects(providerGetDataObjects, providers, statusList);
    ProviderGetAllReturnModelResult providerGetAllReturnModelResult =
        new ProviderGetAllReturnModelResult()
            .data(providerGetDataObjects)
            .page(pageNo)
            .pageSize(pageSizeActual)
            .numberOfPages(providersPage.getTotalPages());
    return new ProviderGetAllReturnModel().ok(true).result(providerGetAllReturnModelResult);
  }
}
