package com.provider.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.provider.model.ItemRequestModel;
import com.provider.model.ItemReturnModel;
import com.provider.model.ItemReturnModelResult;
import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderReturnModel;
import com.provider.model.ProviderReturnModelResult;
import com.provider.model.StatusEnum;
import com.provider.model.SubItemRequestModel;
import com.provider.model.SubItemReturnModel;
import com.provider.persistence.entity.Item;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.entity.SubItem;

@Component
public class EntityConverter {

    @Autowired
    private ModelMapper modelMapper;
    
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

}
