package com.provider.service;

import com.provider.model.ProviderGetDataObject;
import com.provider.model.StatusEnum;
import com.provider.persistence.entity.Item;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.repository.ItemRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelPopulationService {
  private final ItemRepository itemRepository;

  private final EntityConverterService entityConverterService;

  public void populateProviderGetDataObject(
      List<ProviderGetDataObject> providerGetDataObjects,
      List<Provider> providers,
      List<StatusEnum> statusList) {
    for (Provider provider : providers) {
      provider.setItems(
          itemRepository.findTop10ByProviderIdAndStatusIn(provider.getId(), statusList));
      ProviderGetDataObject getDataObject =
          entityConverterService.convertProviderToGetDataObject(provider);
      List<String> services =
          provider.getItems().stream().map(Item::getTitle).collect(Collectors.toList());
      getDataObject.setServices(services);
      providerGetDataObjects.add(getDataObject);
    }
  }
}
