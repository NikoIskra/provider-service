package com.provider.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.provider.exception.BadRequestException;
import com.provider.model.AccountRoleIDReturnModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountApiClient {
    private final RestTemplate restTemplate;

    public void verifyAccountID (UUID accountID) {
        try {
        AccountRoleIDReturnModel accountRoleIDReturnModel = restTemplate.getForObject(
            "/api/v1/account/{account_id}/role/PROVIDER",
             AccountRoleIDReturnModel.class,
             accountID.toString());
        } catch (Exception e) {
            throw new BadRequestException("No provider role for this account found");
        }
    }

    
}
