package com.provider.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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

    @Mock
    AccountApiClient accountApiClient;

    @InjectMocks
    @Spy
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
        when(providerRepository.existsByName(anyString())).thenReturn(false);
        assertDoesNotThrow(
            () -> providerValidator.validateProviderRequest(uuid, providerRequestModel)
        );
        verify(accountApiClient).verifyAccountID(uuid);
        verify(providerRepository).existsByName(anyString());
    }
    

    @Test
    void testValidateProviderRequestData_nameExists() {
        ProviderRequestModel providerRequestModel = spy(createProviderRequestModel());
        doNothing().when(accountApiClient).verifyAccountID(uuid);
        when(providerRepository.existsByName(any())).thenReturn(true);
        assertThrows(BadRequestException.class,
        () -> providerValidator.validateProviderRequest(uuid, providerRequestModel)    
        );
        verify(providerRepository).existsByName(anyString());
        verify(providerRequestModel, never()).getPhoneNumber();
    }

    @Test
    void testValidateProviderRequestData_invalidRequestModel() {
        ProviderRequestModel providerRequestModel = createInvalidProviderRequestModel();
        assertThrows(BadRequestException.class,
        () -> providerValidator.validateProviderRequest(uuid, providerRequestModel)    
        );
        verify(providerRepository).existsByName(anyString());
    }
    
}