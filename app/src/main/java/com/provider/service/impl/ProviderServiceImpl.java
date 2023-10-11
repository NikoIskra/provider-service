package com.provider.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.provider.exception.NotFoundException;
import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderReturnModel;
import com.provider.model.ProviderReturnModelResult;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.repository.ProviderRepository;
import com.provider.service.AccountApiClient;
import com.provider.service.ProviderService;
import com.provider.service.ProviderValidator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {
    
    private final ProviderRepository providerRepository;
    
    private final ProviderValidator providerValidator;

    private Provider mapRequestModelToProvider(ProviderRequestModel providerRequestModel) {
        Provider provider = new Provider();
        provider.setName(providerRequestModel.getName());
        provider.setTitle(providerRequestModel.getTitle());
        provider.setDescription(providerRequestModel.getDescription());
        provider.setPhoneNumber(providerRequestModel.getPhoneNumber());
        provider.setStatus("view-only");
        return provider;
    }
    
    private ProviderReturnModel mapProviderToReturnModel(Provider provider) {
        ProviderReturnModelResult providerReturnModelResult = new ProviderReturnModelResult()
        .id(provider.getId())
        .ownerId(provider.getOwnerId())
        .name(provider.getName())
        .title(provider.getTitle())
        .phoneNumber(provider.getPhoneNumber())
        .description(provider.getDescription())
        .status(provider.getStatus());
        return new ProviderReturnModel().ok(true).result(providerReturnModelResult);
    }
    
    @Override
    @Transactional
    public ProviderReturnModel save(UUID accountID, ProviderRequestModel providerRequestModel) {
        providerValidator.validateProviderRequest(accountID, providerRequestModel);
        Provider provider = mapRequestModelToProvider(providerRequestModel);
        provider.setOwnerId(accountID);
        providerRepository.saveAndFlush(provider);
        return mapProviderToReturnModel(provider);
    }
    
}
