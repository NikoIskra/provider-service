package com.provider.service;

import com.provider.exception.BadRequestException;
import com.provider.exception.NotFoundException;
import com.provider.model.StatusEnum;
import com.provider.persistence.entity.Item;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.repository.ItemRepository;
import com.provider.persistence.repository.ProviderRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    } else if (!provider.get().getOwnerId().equals(accountID)) {
      throw new BadRequestException("Providers owner ID and supplied owner ID do not match!");
    }
  }

  public void validateItemPut(UUID accountID, Long providerID, Long itemID) {
    if (!providerRepository.existsByIdAndOwnerId(providerID, accountID)) {
      throw new BadRequestException("No record with provider and owner id found");
    }
    if (!itemRepository.existsByIdAndProviderId(itemID, providerID)) {
      throw new BadRequestException("No item record found with supplied ID and Provider ID");
    }
  }

  public void validateItemGet(UUID accountID, Long providerID, Long itemID) {
    accountApiClient.verifyAccountID(accountID);
    Optional<Item> item = itemRepository.findById(itemID);
    if (item.isEmpty()) {
      throw new NotFoundException("No item with matching ID found");
    } else if (item.get().getStatus().equals(StatusEnum.CANCELLED)) {
      throw new NotFoundException("Item with provided id is cancelled");
    }
  }
}
