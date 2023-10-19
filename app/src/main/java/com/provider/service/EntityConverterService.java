package com.provider.service;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.provider.config.Configuration;
import com.provider.model.ItemRequestModel;
import com.provider.model.ItemReturnModel;
import com.provider.model.ItemReturnModelResult;
import com.provider.model.ItemUpdateRequestModel;
import com.provider.model.ItemUpdateReturnModel;
import com.provider.model.ItemUpdateReturnModelResult;
import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderReturnModel;
import com.provider.model.ProviderReturnModelResult;
import com.provider.model.ProviderUpdateRequestModel;
import com.provider.model.StatusEnum;
import com.provider.model.SubItemRequestModel;
import com.provider.model.SubItemReturnModel;
import com.provider.persistence.entity.Item;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.entity.SubItem;

import lombok.RequiredArgsConstructor;


@Service
public class EntityConverterService {

    private final ModelMapper modelMapper;
    private final ModelMapper strictModelMapper;

    public EntityConverterService (@Qualifier("strictModelMapper") ModelMapper strictModelMapper, 
                   @Qualifier("modelMapper") ModelMapper modelMapper) {
        this.strictModelMapper = strictModelMapper;
        this.modelMapper = modelMapper;
    }


    public ProviderReturnModel convertProviderToReturnModel (Provider provider) {
        ProviderReturnModelResult providerReturnModelResult = modelMapper.map(provider, ProviderReturnModelResult.class);
        return new ProviderReturnModel().ok(true).result(providerReturnModelResult);
    }

    public Provider convertProviderRequestModelToProvider (ProviderRequestModel providerRequestModel) {
        Provider provider = modelMapper.map(providerRequestModel, Provider.class);
        provider.setStatus(StatusEnum.VIEW_ONLY);
        return provider;
    }

    public SubItem convertSubItemRequestModelToSubItem (SubItemRequestModel subItemRequestModel) {
        SubItem subItem = modelMapper.map(subItemRequestModel, SubItem.class);
        subItem.setStatus(StatusEnum.VIEW_ONLY);
        return subItem;
    }

    public SubItemReturnModel convertSubItemToReturnModel (SubItem subItem) {
        return modelMapper.map(subItem, SubItemReturnModel.class);
    }

    public Item convertItemRequestModelToItem (ItemRequestModel itemRequestModel) {
        Item item = modelMapper.map(itemRequestModel, Item.class);
        item.setStatus(StatusEnum.VIEW_ONLY);
        return item;
    }

    public ItemReturnModel convertItemToReturnModel (Item item) {
        ItemReturnModelResult itemReturnModelResult = modelMapper.map(item, ItemReturnModelResult.class);
        return new ItemReturnModel().ok(true).result(itemReturnModelResult);
    }

    public void patchRequestModelToProvider (ProviderUpdateRequestModel providerUpdateRequestModel, Provider provider) {
        modelMapper.map(providerUpdateRequestModel, provider);
    }

    public void updateItemUpdateModelToItem (ItemUpdateRequestModel itemUpdateRequestModel, Item item) {
        strictModelMapper.map(itemUpdateRequestModel, item);
    }

    public ItemUpdateReturnModel convertItemToUpdateReturnModel (Item item) {
        ItemUpdateReturnModelResult itemUpdateReturnModelResult = modelMapper.map(item, ItemUpdateReturnModelResult.class);
        return new ItemUpdateReturnModel().ok(true).result(itemUpdateReturnModelResult);
    }

}
