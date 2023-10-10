package com.provider.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.provider.exception.BadRequestException;
import com.provider.model.AccountRoleIDReturnModel;
import com.provider.model.ProviderRequestModel;
import com.provider.persistence.repository.ProviderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProviderValidator {

    private final RestTemplate restTemplate;

    private final ProviderRepository providerRepository;

    public void validateProviderRequest(UUID accountID, ProviderRequestModel providerRequestModel) {
        String url="http://localhost:3000/api/v1/account/" + accountID.toString() + "/role/PROVIDER";
        try {
        AccountRoleIDReturnModel accountRoleIDReturnModel = restTemplate.getForObject(url, AccountRoleIDReturnModel.class);
        } catch (Exception e) {
            throw new BadRequestException("No provider role for this account found");
        }
        if (providerRepository.existsByName(providerRequestModel.getName())) {
            throw new BadRequestException("Provider with that name already exists");
        }
        if (!providerRequestModel.getPhoneNumber().matches("^\\+?\\d{10,14}$")) {
            throw new BadRequestException("Phone number not valid");
        }
    }
}
