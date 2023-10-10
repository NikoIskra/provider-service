package com.provider.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.provider.exception.BadRequestException;
import com.provider.model.AccountRoleIDReturnModel;
import com.provider.model.AccountRoleIDReturnModelResult;
import com.provider.model.ProviderRequestModel;

@ExtendWith(MockitoExtension.class)
public class AccountApiClientTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    AccountApiClient accountApiClient;

    private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

    private static AccountRoleIDReturnModel createAccountRoleIDReturnModel() {
        AccountRoleIDReturnModelResult accountRoleIDReturnModelResult = new AccountRoleIDReturnModelResult()
        .roleId(uuid);
        AccountRoleIDReturnModel accountRoleIDReturnModel = new AccountRoleIDReturnModel().ok(true).result(accountRoleIDReturnModelResult);
        return accountRoleIDReturnModel;
    }

    @Test
    void testVerifyAccountID() {
        AccountRoleIDReturnModel accountRoleIDReturnModel = createAccountRoleIDReturnModel();
        when(restTemplate.getForObject("/api/v1/account/{account_id}/role/PROVIDER",
        AccountRoleIDReturnModel.class,
        uuid.toString())).thenReturn(accountRoleIDReturnModel);
        assertDoesNotThrow(
            () -> accountApiClient.verifyAccountID(uuid)
        );
    }
    
    @Test
    void testVerifyAccountID_exceptionThrown() {
                when(restTemplate.getForObject("/api/v1/account/{account_id}/role/PROVIDER",
        AccountRoleIDReturnModel.class,
        uuid.toString())).thenThrow(RestClientException.class);
        assertThrows(BadRequestException.class,
            () -> accountApiClient.verifyAccountID(uuid)
        );
    }
}
