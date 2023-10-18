package com.provider.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.provider.model.ItemRequestModel;
import com.provider.model.ItemReturnModel;
import com.provider.model.ItemUpdateRequestModel;
import com.provider.model.ItemUpdateReturnModel;
import com.provider.model.SubItemRequestModel;
import com.provider.persistence.entity.Item;
import com.provider.persistence.entity.SubItem;
import com.provider.persistence.repository.ItemRepository;
import com.provider.persistence.repository.ProviderRepository;
import com.provider.service.EntityConverterService;
import com.provider.service.ItemService;
import com.provider.service.ItemValidator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemValidator itemValidator;

    private final ItemRepository itemRepository;

    private final ProviderRepository providerRepository;

    private final EntityConverterService entityConverter;

    @Override
    @Transactional
    public ItemReturnModel save(UUID accountID, Long providerID, ItemRequestModel itemRequestModel) {
        itemValidator.validateItemRequest(accountID, providerID);
        Item item = entityConverter.convertItemRequestModelToItem(itemRequestModel);
        item.setProvider(providerRepository.getById(providerID));
        List<SubItem> subItems = new ArrayList<>();
        if (!CollectionUtils.isEmpty(itemRequestModel.getSubItems())) {
            for (SubItemRequestModel subItemRequestModel : itemRequestModel.getSubItems()) {
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
    public ItemUpdateReturnModel put(UUID accountID, Long ProviderID, Long itemID,
            ItemUpdateRequestModel itemUpdateRequestModel) {
        itemValidator.validateItemPut(accountID, ProviderID, itemID);
        Item item = itemRepository.getById(itemID);
        item.setTitle(itemUpdateRequestModel.getTitle());
        item.setPriceCents(itemUpdateRequestModel.getPriceCents());
        if (StringUtils.isNotEmpty(itemUpdateRequestModel.getDescription())) {
            item.setDescription(itemUpdateRequestModel.getDescription());
        }
        if (!ObjectUtils.allNull(itemUpdateRequestModel.getStatus())) {
            item.setStatus(itemUpdateRequestModel.getStatus());
        }
        itemRepository.save(item);
        return entityConverter.convertItemToUpdateReturnModel(item);
    }
}
