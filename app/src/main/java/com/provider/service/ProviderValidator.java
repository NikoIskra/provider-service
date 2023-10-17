package com.provider.service;

import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.provider.exception.BadRequestException;
import com.provider.model.AccountRoleIDReturnModel;
import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderUpdateRequestModel;
import com.provider.persistence.repository.ProviderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProviderValidator {

    private final ProviderRepository providerRepository;

    private final AccountApiClient accountApiClient;

    public void validateProviderRequest(UUID accountID, ProviderRequestModel providerRequestModel) {
        accountApiClient.verifyAccountID(accountID);
        if (providerRepository.existsByName(providerRequestModel.getName())) {
            throw new BadRequestException("Provider with that name already exists");
        }
        if (!providerRequestModel.getPhoneNumber().matches("^\\+?\\d{10,14}$")) {
            throw new BadRequestException("Phone number not valid");
        }
    }

    public void validateProviderPatchRequest(UUID accountID, Long providerID, ProviderUpdateRequestModel providerUpdateRequestModel) {
        accountApiClient.verifyAccountID(accountID);
        if (!providerRepository.existsByIdAndOwnerId(providerID, accountID)) {
            throw new BadRequestException("No record with provider and owner id found");
        }
        if (ObjectUtils.allNull(providerUpdateRequestModel.getDescription(), providerUpdateRequestModel.getPhoneNumber(), providerUpdateRequestModel.getTitle(), providerUpdateRequestModel.getStatus())) {
            throw new BadRequestException("Request body cannot be empty!");
        }
    }
}
