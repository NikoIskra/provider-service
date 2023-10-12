package com.provider.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.provider.exception.BadRequestException;
import com.provider.model.AccountRoleIDReturnModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountApiClient {

    @Value("${base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public void verifyAccountID (UUID accountID) {
        String url = baseUrl + "/api/v1/account/" + accountID.toString() + "/role/PROVIDER";
        try {
        AccountRoleIDReturnModel accountRoleIDReturnModel = restTemplate.getForObject(
            url,
             AccountRoleIDReturnModel.class);
        } catch (Exception e) {
            throw new BadRequestException("No provider role for this account found");
        }
    }

    
}
