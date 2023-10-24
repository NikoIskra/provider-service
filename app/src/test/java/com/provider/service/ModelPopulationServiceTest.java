package com.provider.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.provider.model.ProviderGetDataObject;
import com.provider.model.StatusEnum;
import com.provider.persistence.entity.Item;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.repository.ItemRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ModelPopulationServiceTest {

  @Mock ItemRepository itemRepository;

  @Mock EntityConverterService entityConverterService;

  @InjectMocks ModelPopulationService modelPopulationService;

  private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

  private static Provider createProvider() {
    Provider provider = new Provider(uuid, "name", "title", "1234567890", StatusEnum.VIEW_ONLY);
    provider.setId(1L);
    return provider;
  }

  private static Item createItem() {
    Item item = new Item("itemtitle", 1200, StatusEnum.VIEW_ONLY, null, null);
    return item;
  }

  private static ProviderGetDataObject createProviderGetDataObject() {
    ProviderGetDataObject providerGetDataObject =
        new ProviderGetDataObject()
            .id(1L)
            .name("name")
            .title("title")
            .phoneNumber("1234567890")
            .status(StatusEnum.VIEW_ONLY);
    return providerGetDataObject;
  }

  @Test
  void testPopulateProviderGetDataObject() {
    List<StatusEnum> statusList = new ArrayList<>();
    for (StatusEnum status : StatusEnum.values()) {
      if (!status.equals(StatusEnum.CANCELLED)) {
        statusList.add(status);
      }
    }
    Provider provider = createProvider();
    List<Provider> providers = List.of(provider);
    List<ProviderGetDataObject> providerGetDataObjects = new ArrayList<>();
    Item item = createItem();
    List<Item> items = List.of(item);
    ProviderGetDataObject providerGetDataObject = createProviderGetDataObject();
    when(itemRepository.findTop10ByProviderIdAndStatusIn(1L, statusList)).thenReturn(items);
    when(entityConverterService.convertProviderToGetDataObject(any()))
        .thenReturn(providerGetDataObject);
    assertDoesNotThrow(
        () ->
            modelPopulationService.populateProviderGetDataObject(
                providerGetDataObjects, providers, statusList));
    assertEquals(providerGetDataObjects.get(0), providerGetDataObject);
    verify(itemRepository).findTop10ByProviderIdAndStatusIn(1L, statusList);
    verify(entityConverterService).convertProviderToGetDataObject(any());
  }
}
