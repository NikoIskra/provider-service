package com.provider.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.provider.exception.BadRequestException;
import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderUpdateRequestModel;
import com.provider.model.StatusEnum;
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

    private static ProviderUpdateRequestModel createProviderUpdateRequestModel() {
        ProviderUpdateRequestModel providerUpdateRequestModel = new ProviderUpdateRequestModel()
        .description("updatedesc")
        .status(StatusEnum.ACTIVE)
        .title("updatedTitle");
        return providerUpdateRequestModel;
    }

    private static ProviderUpdateRequestModel createEmptyProviderUpdateRequestModel() {
        return new ProviderUpdateRequestModel();
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
    void testValidateProviderRequestData_noAccount() {
        ProviderRequestModel providerRequestModel = createProviderRequestModel();
        doThrow(new BadRequestException(null)).when(accountApiClient).verifyAccountID(uuid);
        assertThrows(BadRequestException.class,
        () -> providerValidator.validateProviderRequest(uuid, providerRequestModel)    
        );
        verifyNoInteractions(providerRepository);
    }

    @Test
    void testValidateProviderRequestData_nameExists() {
        ProviderRequestModel providerRequestModel = createProviderRequestModel();
        doNothing().when(accountApiClient).verifyAccountID(uuid);
        when(providerRepository.existsByName(any())).thenReturn(true);
        assertThrows(BadRequestException.class,
        () -> providerValidator.validateProviderRequest(uuid, providerRequestModel)    
        );
        verify(accountApiClient).verifyAccountID(uuid);
        verify(providerRepository).existsByName(anyString());
    }

    @Test
    void testValidateProviderRequestData_invalidRequestModel() {
        ProviderRequestModel providerRequestModel = createInvalidProviderRequestModel();
        assertThrows(BadRequestException.class,
        () -> providerValidator.validateProviderRequest(uuid, providerRequestModel)    
        );
        verify(accountApiClient).verifyAccountID(uuid);
        verify(providerRepository).existsByName(anyString());
    }

    @Test
    void testValidateProviderPatchRequest() {
        ProviderUpdateRequestModel providerUpdateRequestModel = createProviderUpdateRequestModel();
        when(providerRepository.existsByIdAndOwnerId(1L, uuid)).thenReturn(true);
        assertDoesNotThrow(
            () -> providerValidator.validateProviderPatchRequest(uuid, 1L, providerUpdateRequestModel)
        );
        verify(accountApiClient).verifyAccountID(uuid);
        verify(providerRepository).existsByIdAndOwnerId(1L, uuid);
    }
    
    @Test
    void testValidateProviderPatchRequest_noAccount() {
        ProviderUpdateRequestModel providerUpdateRequestModel = createProviderUpdateRequestModel();
        doThrow(new BadRequestException(null)).when(accountApiClient).verifyAccountID(uuid);
        assertThrows(BadRequestException.class,
            () -> providerValidator.validateProviderPatchRequest(uuid, 1L, providerUpdateRequestModel)
        );
        verify(accountApiClient).verifyAccountID(uuid);
        verifyNoInteractions(providerRepository);
    }

    @Test
    void testValidateProviderPatchRequest_noRecord() {
        ProviderUpdateRequestModel providerUpdateRequestModel = createProviderUpdateRequestModel();
        when(providerRepository.existsByIdAndOwnerId(1L, uuid)).thenReturn(false);
        assertThrows(BadRequestException.class,
            () -> providerValidator.validateProviderPatchRequest(uuid, 1L, providerUpdateRequestModel)
        );
        verify(accountApiClient).verifyAccountID(uuid);
        verify(providerRepository).existsByIdAndOwnerId(1L, uuid);
    }

    @Test
    void testValidateProviderPatchRequest_emptyBody() {
        ProviderUpdateRequestModel providerUpdateRequestModel = createEmptyProviderUpdateRequestModel();
        when(providerRepository.existsByIdAndOwnerId(1L, uuid)).thenReturn(true);
        assertThrows(BadRequestException.class,
            () -> providerValidator.validateProviderPatchRequest(uuid, 1L, providerUpdateRequestModel)
        );
        verify(accountApiClient).verifyAccountID(uuid);
        verify(providerRepository).existsByIdAndOwnerId(1L, uuid);
    }
    
}