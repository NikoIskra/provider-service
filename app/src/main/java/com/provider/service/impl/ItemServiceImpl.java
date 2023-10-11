package com.provider.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.provider.model.ItemRequestModel;
import com.provider.model.ItemReturnModel;
import com.provider.model.ItemReturnModelResult;
import com.provider.model.SubItemRequestModel;
import com.provider.model.SubItemReturnModel;
import com.provider.persistence.entity.Item;
import com.provider.persistence.entity.SubItem;
import com.provider.persistence.repository.ItemRepository;
import com.provider.persistence.repository.ProviderRepository;
import com.provider.service.ItemService;
import com.provider.service.ItemValidator;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    
    private final ItemValidator itemValidator;

    private final ItemRepository itemRepository;

    private final ProviderRepository providerRepository;

    private final EntityManager entityManager;

    private SubItem createSubItem(SubItemRequestModel subItemRequestModel, Item item) {
        SubItem subItem = new SubItem();
        subItem.setTitle(subItemRequestModel.getTitle());
        subItem.setDescription(subItemRequestModel.getDescription());
        subItem.setPriceCents(subItemRequestModel.getPriceCents());
        subItem.setStatus("view-only");
        subItem.setItem(item);
        return subItem;
    }

    private Item mapRequestModelToItem(ItemRequestModel itemRequestModel, Long providerID) {
        Item item = new Item();
        item.setTitle(itemRequestModel.getTitle());
        item.setDescription(itemRequestModel.getDescription());
        item.setPriceCents(itemRequestModel.getPriceCents());
        item.setStatus("view-only");
        item.setProvider(providerRepository.getById(providerID));
        return item;
    }

    private ItemReturnModel mapItemToReturnModel(Item item) {
        List<SubItemReturnModel> subItemReturnModels = new ArrayList<>();
        List<SubItem> subItems = item.getSubItems();
        if (!subItems.isEmpty()) {
            for (SubItem subItem : subItems) {
                SubItemReturnModel subItemReturnModel = new SubItemReturnModel();
                subItemReturnModel.setId(subItem.getId());
                subItemReturnModel.setItemId(subItem.getItem().getId());
                subItemReturnModel.setTitle(subItem.getTitle());
                subItemReturnModel.setDescription(subItem.getDescription());
                subItemReturnModel.setPriceCents(subItem.getPriceCents());
                subItemReturnModel.setStatus(subItem.getStatus());
                subItemReturnModels.add(subItemReturnModel);
            }
        }
        ItemReturnModelResult itemReturnModelResult = new ItemReturnModelResult()
        .id(item.getId())
        .providerId(item.getProvider().getId())
        .title(item.getTitle())
        .description(item.getDescription())
        .priceCents(item.getPriceCents())
        .status(item.getStatus())
        .subItems(subItemReturnModels);
        return new ItemReturnModel().ok(true).result(itemReturnModelResult);
    }
    
    @Override
    @Transactional
    public ItemReturnModel save(UUID accountID, Long providerID, ItemRequestModel itemRequestModel) {
        itemValidator.validateItemRequest(accountID, providerID);
        Item item = mapRequestModelToItem(itemRequestModel, providerID);
        List<SubItem> subItems = new ArrayList<>();
        if (!itemRequestModel.getSubItems().isEmpty()) {
            for (SubItemRequestModel subItemRequestModel : itemRequestModel.getSubItems()) {
                SubItem subItem = createSubItem(subItemRequestModel, item);
                subItems.add(subItem);
            }
            item.setSubItems(subItems);
        }
        itemRepository.saveAndFlush(item);
        entityManager.refresh(item);
        return mapItemToReturnModel(item);
    } 
}
