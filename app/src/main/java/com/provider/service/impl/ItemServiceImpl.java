package com.provider.service.impl;

import com.provider.model.ItemGetReturnModel;
import com.provider.model.ItemPostRequestModel;
import com.provider.model.ItemPostReturnModel;
import com.provider.model.ItemPostSubItemModel;
import com.provider.model.ItemUpdateRequestModel;
import com.provider.model.ItemUpdateReturnModel;
import com.provider.model.SubItemPostRequestModel;
import com.provider.persistence.entity.Item;
import com.provider.persistence.entity.SubItem;
import com.provider.persistence.repository.ItemRepository;
import com.provider.persistence.repository.ProviderRepository;
import com.provider.service.EntityConverterService;
import com.provider.service.ItemService;
import com.provider.service.ItemValidator;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

  private final ItemValidator itemValidator;

  private final ItemRepository itemRepository;

  private final ProviderRepository providerRepository;

  private final EntityConverterService entityConverter;

  @Override
  @Transactional
  public ItemPostReturnModel save(UUID accountID, Long providerID, ItemPostRequestModel itemRequestModel) {
    itemValidator.validateItemRequest(accountID, providerID);
    Item item = entityConverter.convertItemRequestModelToItem(itemRequestModel);
    item.setProvider(providerRepository.getById(providerID));
    List<SubItem> subItems = new ArrayList<>();
    if (!CollectionUtils.isEmpty(itemRequestModel.getSubItems())) {
      for (SubItemPostRequestModel subItemRequestModel : itemRequestModel.getSubItems()) {
        SubItem subItem = entityConverter.convertSubItemRequestModelToSubItem(subItemRequestModel);
        subItem.setItem(item);
        subItems.add(subItem);
      }
    }
    item.setSubItems(subItems);
    itemRepository.saveAndFlush(item);
    return entityConverter.convertItemToReturnModel(item);
  }

  @Override
  public ItemUpdateReturnModel put(
      UUID accountID, Long ProviderID, Long itemID, ItemUpdateRequestModel itemUpdateRequestModel) {
    itemValidator.validateItemPut(accountID, ProviderID, itemID);
    Item item = itemRepository.getById(itemID);
    entityConverter.updateItemUpdateModelToItem(itemUpdateRequestModel, item);
    itemRepository.save(item);
    return entityConverter.convertItemToUpdateReturnModel(item);
  }

  @Override
  public ItemGetReturnModel get(UUID accountID, Long ProviderID, Long itemID) {
    itemValidator.validateItemGet(accountID, ProviderID, itemID);
    Item item = itemRepository.getItemWithParentAndListOfChildren(ProviderID, itemID);
    return entityConverter.convertItemToGetReturnModle(item);
  }
}
