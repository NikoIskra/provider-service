package com.provider.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.provider.model.ItemRequestModel;
import com.provider.model.ItemReturnModel;
import com.provider.model.ItemReturnModelResult;
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

@ExtendWith(MockitoExtension.class)
public class EntityConverterTest {
    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    EntityConverterService entityConverterService;

    private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

    private static ProviderRequestModel createProviderRequestModel() {
        ProviderRequestModel providerRequestModel = new ProviderRequestModel("name", "title", "1234567890");
        return providerRequestModel;
    }

    private static Provider createProvider() {
        Provider provider = new Provider(uuid, "name", "title", "1234567890", StatusEnum.VIEW_ONLY);
        return provider;
    }

    private static ProviderReturnModelResult createProviderReturnModelResult() {
        ProviderReturnModelResult providerReturnModelResult = new ProviderReturnModelResult()
                .id(1L)
                .name("name")
                .ownerId(uuid)
                .status(StatusEnum.VIEW_ONLY)
                .title("title")
                .phoneNumber("1234567890");
        return providerReturnModelResult;
    }

    private static SubItemRequestModel createSubItemRequestModel() {
        SubItemRequestModel subItemRequestModel = new SubItemRequestModel("subitemtitle", 1300);
        return subItemRequestModel;
    }

    private static SubItem createSubItem() {
        return new SubItem("subitemtitle", 1300, StatusEnum.VIEW_ONLY, null);
    }

    private static SubItemReturnModel createSubItemReturnModel() {
        SubItemReturnModel subItemReturnModel = new SubItemReturnModel()
                .title("subitemtitle")
                .priceCents(1300)
                .status(StatusEnum.VIEW_ONLY);
        return subItemReturnModel;
    }

    private static Item createItem() {
        Item item = new Item("itemtitle", 1200, StatusEnum.VIEW_ONLY, null, null);
        return item;
    }

    private static ItemReturnModelResult createItemReturnModelResult() {
        ItemReturnModelResult itemReturnModelResult = new ItemReturnModelResult()
                .title("itemtitle")
                .priceCents(1200)
                .status(StatusEnum.VIEW_ONLY);
        return itemReturnModelResult;
    }

    private static ItemRequestModel createItemRequestModel() {
        ItemRequestModel itemRequestModel = new ItemRequestModel("itemtitle", 1200);
        return itemRequestModel;
    }

    @Test
    void testConvertProviderToReturnModel() {
        Provider provider = createProvider();
        ProviderReturnModelResult providerReturnModelResult = createProviderReturnModelResult();
        when(modelMapper.map(provider, ProviderReturnModelResult.class)).thenReturn(providerReturnModelResult);
        ProviderReturnModel returnModel = entityConverterService.convertProviderToReturnModel(provider);
        verify(modelMapper).map(any(), any());
        assertEquals(returnModel.isOk(), true);
        assertEquals(returnModel.getResult().getId(), 1L);
        assertEquals(returnModel.getResult().getName(), provider.getName());
        assertEquals(returnModel.getResult().getTitle(), provider.getTitle());
        assertEquals(returnModel.getResult().getPhoneNumber(), provider.getPhoneNumber());
        assertEquals(returnModel.getResult().getStatus(), provider.getStatus());
    }

    @Test
    void testConvertProviderRequestModelToProvider() {
        ProviderRequestModel providerRequestModel = createProviderRequestModel();
        Provider provider = createProvider();
        when(modelMapper.map(providerRequestModel, Provider.class)).thenReturn(provider);
        Provider provider2 = entityConverterService.convertProviderRequestModelToProvider(providerRequestModel);
        verify(modelMapper).map(any(), any());
        assertEquals(provider2.getStatus(), StatusEnum.VIEW_ONLY);
        assertEquals(provider2.getName(), providerRequestModel.getName());
        assertEquals(provider2.getTitle(), providerRequestModel.getTitle());
        assertEquals(provider2.getPhoneNumber(), providerRequestModel.getPhoneNumber());
    }

    @Test
    void testConvertSubItemRequestModelToSubItem() {
        SubItemRequestModel subItemRequestModel = createSubItemRequestModel();
        SubItem subItem = createSubItem();
        when(modelMapper.map(subItemRequestModel, SubItem.class)).thenReturn(subItem);
        SubItem subItem2 = entityConverterService.convertSubItemRequestModelToSubItem(subItemRequestModel);
        assertEquals(subItemRequestModel.getTitle(), subItem2.getTitle());
        assertEquals(subItemRequestModel.getDescription(), subItem2.getDescription());
        assertEquals(subItemRequestModel.getPriceCents(), subItem2.getPriceCents());
        assertEquals(subItem2.getStatus(), StatusEnum.VIEW_ONLY);
    }

    @Test
    void testConvertSubItemToReturnModel() {
        SubItemReturnModel subItemReturnModel = createSubItemReturnModel();
        SubItem subItem = createSubItem();
        when(modelMapper.map(subItem, SubItemReturnModel.class)).thenReturn(subItemReturnModel);
        SubItemReturnModel returnModel = entityConverterService.convertSubItemToReturnModel(subItem);
        assertEquals(returnModel.getTitle(), subItem.getTitle());
        assertEquals(returnModel.getPriceCents(), subItem.getPriceCents());
        assertEquals(returnModel.getStatus(), StatusEnum.VIEW_ONLY);
    }

    @Test
    void testConvertItemRequestModelToItem() {
        ItemRequestModel itemRequestModel = createItemRequestModel();
        Item item = createItem();
        when(modelMapper.map(itemRequestModel, Item.class)).thenReturn(item);
        Item returnItem = entityConverterService.convertItemRequestModelToItem(itemRequestModel);
        assertEquals(itemRequestModel.getTitle(), returnItem.getTitle());
        assertEquals(itemRequestModel.getPriceCents(), returnItem.getPriceCents());
        assertEquals(StatusEnum.VIEW_ONLY, returnItem.getStatus());
    }

    @Test
    void testConvertItemToReturnModel() {
        Item item = createItem();
        ItemReturnModelResult itemReturnModelResult = createItemReturnModelResult();
        when(modelMapper.map(item, ItemReturnModelResult.class)).thenReturn(itemReturnModelResult);
        ItemReturnModel itemReturnModel = entityConverterService.convertItemToReturnModel(item);
        assertEquals(itemReturnModel.isOk(), true);
        assertEquals(itemReturnModel.getResult().getTitle(), item.getTitle());
        assertEquals(itemReturnModel.getResult().getPriceCents(), item.getPriceCents());
        assertEquals(itemReturnModel.getResult().getStatus(), item.getStatus());
    }

}
