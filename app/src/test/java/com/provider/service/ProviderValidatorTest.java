package com.provider.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.provider.exception.BadRequestException;
import com.provider.model.AccountRoleIDReturnModel;
import com.provider.model.AccountRoleIDReturnModelResult;
import com.provider.model.ProviderRequestModel;
import com.provider.persistence.repository.ProviderRepository;

@ExtendWith(MockitoExtension.class)
public class ProviderValidatorTest {
    @Mock
    ProviderRepository providerRepository;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    ProviderValidator providerValidator;

    private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

    private static ProviderRequestModel createProviderRequestModel() {
        ProviderRequestModel providerRequestModel = new ProviderRequestModel("testname", "testtitle", "1234567890");
        return providerRequestModel;
    }

    private static ProviderRequestModel createInvalidProviderRequestModel() {
        ProviderRequestModel providerRequestModel = new ProviderRequestModel("testname", "testtitle", "12345678");
        return providerRequestModel;
    }

    private static AccountRoleIDReturnModel createAccountRoleIDReturnModel() {
        AccountRoleIDReturnModelResult accountRoleIDReturnModelResult = new AccountRoleIDReturnModelResult()
        .roleId(uuid);
        AccountRoleIDReturnModel accountRoleIDReturnModel = new AccountRoleIDReturnModel().ok(true).result(accountRoleIDReturnModelResult);
        return accountRoleIDReturnModel;
    }

    @Test
    void testValidateProviderRequestData() {
        ProviderRequestModel providerRequestModel = createProviderRequestModel();
        AccountRoleIDReturnModel accountRoleIDReturnModel = createAccountRoleIDReturnModel();
        String url="http://localhost:3000/api/v1/account/" + uuid.toString() + "/role/PROVIDER";
        when(restTemplate.getForObject(url, AccountRoleIDReturnModel.class)).thenReturn(accountRoleIDReturnModel);
        when(providerRepository.existsByName(anyString())).thenReturn(false);
        assertDoesNotThrow(
            () -> providerValidator.validateProviderRequest(uuid, providerRequestModel)
        );
    }
    
    @Test
    void testValidateProviderRequestData_noAccount() {
        ProviderRequestModel providerRequestModel = createProviderRequestModel();
        AccountRoleIDReturnModel accountRoleIDReturnModel = createAccountRoleIDReturnModel();
        String url="http://localhost:3000/api/v1/account/" + uuid.toString() + "/role/PROVIDER";
        when(restTemplate.getForObject(url, AccountRoleIDReturnModel.class)).thenThrow(BadRequestException.class);
        assertThrows(BadRequestException.class,
        () -> providerValidator.validateProviderRequest(uuid, providerRequestModel)    
        );
    }

    @Test
    void testValidateProviderRequestData_nameExists() {
        ProviderRequestModel providerRequestModel = createProviderRequestModel();
        AccountRoleIDReturnModel accountRoleIDReturnModel = createAccountRoleIDReturnModel();
        String url="http://localhost:3000/api/v1/account/" + uuid.toString() + "/role/PROVIDER";
        when(restTemplate.getForObject(url, AccountRoleIDReturnModel.class)).thenReturn(accountRoleIDReturnModel);
        when(providerRepository.existsByName(any())).thenReturn(true);
        assertThrows(BadRequestException.class,
        () -> providerValidator.validateProviderRequest(uuid, providerRequestModel)    
        );
    }

    @Test
    void testValidateProviderRequestData_invalidRequestModel() {
        ProviderRequestModel providerRequestModel = createInvalidProviderRequestModel();
        assertThrows(BadRequestException.class,
        () -> providerValidator.validateProviderRequest(uuid, providerRequestModel)    
        );
    }
    
}