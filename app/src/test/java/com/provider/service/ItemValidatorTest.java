package com.provider.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.provider.exception.BadRequestException;
import com.provider.exception.NotFoundException;
import com.provider.model.StatusEnum;
import com.provider.persistence.entity.Item;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.repository.ItemRepository;
import com.provider.persistence.repository.ProviderRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ItemValidatorTest {

  @Mock ProviderRepository providerRepository;

  @Mock AccountApiClient accountApiClient;

  @Mock ItemRepository itemRepository;

  @InjectMocks ItemValidator itemValidator;

  private static Optional<Provider> createProviderOptional() {
    Provider provider = new Provider(uuid, "name", "title", "1234567890", StatusEnum.VIEW_ONLY);
    return Optional.of(provider);
  }

  private static Item createItem() {
    Item item = new Item("itemtitle", 1200, StatusEnum.VIEW_ONLY, null, null);
    return item;
  }

  private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

  private static final UUID uuid2 = UUID.fromString("f67d36e9-cad3-4c2b-9054-c2064509a900");

  @Test
  void testValidateItemRequest() {
    Optional<Provider> provider = createProviderOptional();
    when(providerRepository.findById(1L)).thenReturn(provider);
    assertDoesNotThrow(() -> itemValidator.validateItemRequest(uuid, 1L));
    verify(accountApiClient).verifyAccountID(uuid);
    verify(providerRepository).findById(1L);
  }

  @Test
  void testValidateItemRequest_emptyOptional() {
    when(providerRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(BadRequestException.class, () -> itemValidator.validateItemRequest(uuid, 1L));
    verify(accountApiClient).verifyAccountID(uuid);
    verify(providerRepository).findById(1L);
  }

  @Test
  void testValidateItemRequest_nonMatchingID() {
    Optional<Provider> provider = createProviderOptional();
    when(providerRepository.findById(1L)).thenReturn(provider);
    assertThrows(BadRequestException.class, () -> itemValidator.validateItemRequest(uuid2, 1L));
    verify(accountApiClient).verifyAccountID(uuid2);
    verify(providerRepository).findById(1L);
  }

  @Test
  void testValidateItemPut() {
    when(providerRepository.existsByIdAndOwnerId(any(), any())).thenReturn(true);
    when(itemRepository.existsByIdAndProviderId(any(), any())).thenReturn(true);
    assertDoesNotThrow(() -> itemValidator.validateItemPut(uuid, 1L, 2L));
    verify(accountApiClient).verifyAccountID(uuid);
    verify(providerRepository).existsByIdAndOwnerId(1L, uuid);
    verify(itemRepository).existsByIdAndProviderId(2L, 1L);
  }

  @Test
  void testValidateItemPut_noProvider() {
    when(providerRepository.existsByIdAndOwnerId(any(), any())).thenReturn(false);
    assertThrows(BadRequestException.class, () -> itemValidator.validateItemPut(uuid, 1L, 2L));
    verify(accountApiClient).verifyAccountID(uuid);
    verify(providerRepository).existsByIdAndOwnerId(1L, uuid);
    verifyNoInteractions(itemRepository);
  }

  @Test
  void testValidateItemPut_noItem() {
    when(providerRepository.existsByIdAndOwnerId(any(), any())).thenReturn(true);
    when(itemRepository.existsByIdAndProviderId(any(), any())).thenReturn(false);
    assertThrows(BadRequestException.class, () -> itemValidator.validateItemPut(uuid, 1L, 2L));
    verify(accountApiClient).verifyAccountID(uuid);
    verify(providerRepository).existsByIdAndOwnerId(1L, uuid);
    verify(itemRepository).existsByIdAndProviderId(2L, 1L);
  }

  @Test
  void testValidateItemGet() {
    Item item = createItem();
    when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
    assertDoesNotThrow(() -> itemValidator.validateItemGet(uuid, 1L, 1L));
    verify(itemRepository).findById(1L);
  }

  @Test
  void testValidateItemGet_emptyItem() {
    when(itemRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> itemValidator.validateItemGet(uuid, 1L, 1L));
    verify(itemRepository).findById(1L);
  }

  @Test
  void testValidateItemGet_cancelledItem() {
    Item item = createItem();
    item.setStatus(StatusEnum.CANCELLED);
    when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
    assertThrows(NotFoundException.class, () -> itemValidator.validateItemGet(uuid, 1L, 1L));
    verify(itemRepository).findById(1L);
  }
}
