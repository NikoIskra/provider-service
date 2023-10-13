package com.provider.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.provider.exception.BadRequestException;
import com.provider.model.StatusEnum;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.repository.ItemRepository;
import com.provider.persistence.repository.ProviderRepository;

import jakarta.inject.Inject;

@ExtendWith(MockitoExtension.class)
public class ItemValidatorTest {

    @Mock
    ProviderRepository providerRepository;

    @Mock
    AccountApiClient accountApiClient;

    @InjectMocks
    ItemValidator itemValidator;

    private static Optional<Provider> createProviderOptional() {
        Provider provider = new Provider(uuid, "name", "title", "1234567890", StatusEnum.VIEW_ONLY);
        return Optional.of(provider);
    }

    private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

    private static final UUID uuid2 = UUID.fromString("f67d36e9-cad3-4c2b-9054-c2064509a900");

    @Test
    void testValidateItemRequest() {
        Optional<Provider> provider = createProviderOptional();
        when(providerRepository.findById(1L)).thenReturn(provider);
        assertDoesNotThrow(
            () -> itemValidator.validateItemRequest(uuid, 1L)
        );
        verify(accountApiClient).verifyAccountID(uuid);
        verify(providerRepository).findById(1L);
    }
    
    @Test
    void testValidateItemRequest_emptyOptional() {
        when(providerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class,
            () -> itemValidator.validateItemRequest(uuid, 1L)
        );
        verify(accountApiClient).verifyAccountID(uuid);
        verify(providerRepository).findById(1L);
    }
    
    @Test
    void testValidateItemRequest_nonMatchingID() {
        Optional<Provider> provider = createProviderOptional();
        when(providerRepository.findById(1L)).thenReturn(provider);
        assertThrows(BadRequestException.class,
            () -> itemValidator.validateItemRequest(uuid2, 1L)
        );
        verify(accountApiClient).verifyAccountID(uuid2);
        verify(providerRepository).findById(1L);
    }
    
}
