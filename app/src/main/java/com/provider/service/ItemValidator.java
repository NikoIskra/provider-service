package com.provider.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.provider.exception.BadRequestException;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.repository.ItemRepository;
import com.provider.persistence.repository.ProviderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemValidator {

    private final AccountApiClient accountApiClient;

    private final ProviderRepository providerRepository;

    private final ItemRepository itemRepository;
    
    public void validateItemRequest(UUID accountID, Long providerId) {
        accountApiClient.verifyAccountID(accountID);
        Optional<Provider> provider = providerRepository.findById(providerId);
        if (provider.isEmpty()) {
            throw new BadRequestException("No provider with that ID found");
        }
        else if (!provider.get().getOwnerId().equals(accountID)) {
            throw new BadRequestException("Providers owner ID and supplied owner ID do not match!");
        }
    }

    public void validateItemPut(UUID accountID, Long providerID, Long itemID) {
        accountApiClient.verifyAccountID(accountID);
        if (!providerRepository.existsByIdAndOwnerId(providerID, accountID)) {
            throw new BadRequestException("No record with provider and owner id found");
        }
        if (!itemRepository.existsByIdAndProviderId(itemID, providerID)) {
            throw new BadRequestException("No item record found with supplied ID and Provider ID");
        }
    }
}
