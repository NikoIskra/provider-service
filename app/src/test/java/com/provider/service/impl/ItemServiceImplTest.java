package com.provider.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.provider.converter.EntityConverter;
import com.provider.exception.BadRequestException;
import com.provider.model.ItemRequestModel;
import com.provider.model.ItemReturnModel;
import com.provider.model.ItemReturnModelResult;
import com.provider.model.StatusEnum;
import com.provider.model.SubItemRequestModel;
import com.provider.model.SubItemReturnModel;
import com.provider.persistence.entity.Item;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.entity.SubItem;
import com.provider.persistence.repository.ItemRepository;
import com.provider.persistence.repository.ProviderRepository;
import com.provider.service.ItemValidator;

import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
    @Mock
    ItemRepository itemRepository;

    @Mock
    ItemValidator itemValidator;

    @Mock
    ProviderRepository providerRepository;

    @Mock
    EntityConverter entityConverter;

    @InjectMocks
    ItemServiceImpl itemServiceImpl;

    @Captor
    ArgumentCaptor<Item> itemArgumentCaptor;

    private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

    private static ItemRequestModel createItemRequestModel_1SubItem() {
        ItemRequestModel itemRequestModel = new ItemRequestModel("itemtitle", 1200);
        List<SubItemRequestModel> subItemRequestModels = new ArrayList<>();
        SubItemRequestModel subItemRequestModel = new SubItemRequestModel("subitemtitle", 1300);
        subItemRequestModels.add(subItemRequestModel);
        itemRequestModel.setSubItems(subItemRequestModels);
        return itemRequestModel;
    }

    private static ItemRequestModel createItemRequestModel_emptySubItem() {
        ItemRequestModel itemRequestModel = new ItemRequestModel("itemtitle", 1200);
        List<SubItemRequestModel> subItemRequestModels = new ArrayList<>();
        itemRequestModel.setSubItems(subItemRequestModels);
        return itemRequestModel;
    }

    private static ItemRequestModel createItemRequestModel_nullSubItem() {
        ItemRequestModel itemRequestModel = new ItemRequestModel("itemtitle", 1200);
        return itemRequestModel;
    }

    private static SubItem createSubItem() {
        return new SubItem("subitemtitle", 1300, StatusEnum.VIEW_ONLY, null);
    }

    private static Item createItem() {
        Item item = new Item("itemtitle", 1200, StatusEnum.VIEW_ONLY, null, null);
        return item;
    }

    private static ItemReturnModel createItemReturnModel() {
        List<SubItemReturnModel> subItemReturnModels = new ArrayList<>();
        SubItemReturnModel subItemReturnModel = new SubItemReturnModel()
                .id(1L)
                .itemId(2L)
                .title("subitemtitle")
                .description("desc")
                .priceCents(140)
                .status(StatusEnum.VIEW_ONLY);
        subItemReturnModels.add(subItemReturnModel);
        ItemReturnModelResult itemReturnModelResult = new ItemReturnModelResult()
                .id(1L)
                .providerId(1000L)
                .title("itemtitle")
                .priceCents(1400)
                .status(StatusEnum.VIEW_ONLY)
                .subItems(subItemReturnModels);
        return new ItemReturnModel().ok(true).result(itemReturnModelResult);
    }

    private static Provider createProvider() {
        Provider provider = new Provider(uuid, "providername", "providertitle", "1234567890", StatusEnum.VIEW_ONLY);
        return provider;
    }

    @Test
    void insertItem() {
        ItemRequestModel itemRequestModel = createItemRequestModel_1SubItem();
        doNothing().when(itemValidator).validateItemRequest(uuid, 1L);
        Provider provider = createProvider();
        Item item = createItem();
        ItemReturnModel itemReturnModel = createItemReturnModel();
        when(entityConverter.convertItemRequestModelToItem(itemRequestModel)).thenReturn(item);
        when(providerRepository.getById(1L)).thenReturn(provider);
        when(entityConverter.convertItemToReturnModel(item)).thenReturn(itemReturnModel);
        SubItem subItem = createSubItem();
        when(entityConverter.convertSubItemRequestModelToSubItem(any())).thenReturn(subItem);
        ItemReturnModel itemReturnModel2 = itemServiceImpl.save(uuid, 1L, itemRequestModel);
        verify(itemRepository).saveAndFlush(itemArgumentCaptor.capture());
        Item captureditem = itemArgumentCaptor.getValue();
        assertEquals(captureditem.getTitle(), itemRequestModel.getTitle());
        assertEquals(captureditem.getPriceCents(), itemRequestModel.getPriceCents());
        assertEquals(captureditem.getSubItems().size(), itemRequestModel.getSubItems().size());
        assertEquals(captureditem.getSubItems().get(0).getTitle(), itemRequestModel.getSubItems().get(0).getTitle());
        assertEquals(captureditem.getSubItems().get(0).getPriceCents(), itemRequestModel.getSubItems().get(0).getPriceCents());
        assertEquals(itemRequestModel.getTitle(), itemReturnModel2.getResult().getTitle());
        verify(entityConverter).convertItemRequestModelToItem(itemRequestModel);
        verify(entityConverter).convertItemToReturnModel(item);
        verify(entityConverter).convertSubItemRequestModelToSubItem(any());
        verify(itemValidator).validateItemRequest(uuid, 1L);
        verify(providerRepository).getById(1L);
        verify(itemRepository).saveAndFlush(any());
    }
    
    @Test
    void insertItem_emptySubItems() {
        ItemRequestModel itemRequestModel = createItemRequestModel_emptySubItem();
        doNothing().when(itemValidator).validateItemRequest(uuid, 1L);
        Provider provider = createProvider();
        Item item = createItem();
        ItemReturnModel itemReturnModel = createItemReturnModel();
        when(entityConverter.convertItemRequestModelToItem(itemRequestModel)).thenReturn(item);
        when(providerRepository.getById(1L)).thenReturn(provider);
        when(entityConverter.convertItemToReturnModel(item)).thenReturn(itemReturnModel);
        ItemReturnModel itemReturnModel2 = itemServiceImpl.save(uuid, 1L, itemRequestModel);
        verify(itemRepository).saveAndFlush(itemArgumentCaptor.capture());
        Item captureditem = itemArgumentCaptor.getValue();
        assertEquals(captureditem.getTitle(), itemRequestModel.getTitle());
        assertEquals(captureditem.getPriceCents(), itemRequestModel.getPriceCents());
        assertEquals(captureditem.getSubItems().size(), itemRequestModel.getSubItems().size());
        assertEquals(itemRequestModel.getTitle(), itemReturnModel.getResult().getTitle());
        verify(entityConverter).convertItemRequestModelToItem(itemRequestModel);
        verify(entityConverter).convertItemToReturnModel(item);
        verify(itemValidator).validateItemRequest(uuid, 1L);
        verify(providerRepository).getById(1L);
        verify(itemRepository).saveAndFlush(any());
    }
    
    @Test
    void insertItem_nullSubItems() {
        ItemRequestModel itemRequestModel = createItemRequestModel_nullSubItem();
        doNothing().when(itemValidator).validateItemRequest(uuid, 1L);
        Provider provider = createProvider();
        Item item = createItem();
        ItemReturnModel itemReturnModel = createItemReturnModel();
        when(entityConverter.convertItemRequestModelToItem(itemRequestModel)).thenReturn(item);
        when(providerRepository.getById(1L)).thenReturn(provider);
        when(entityConverter.convertItemToReturnModel(item)).thenReturn(itemReturnModel);
        ItemReturnModel itemReturnModel2 = itemServiceImpl.save(uuid, 1L, itemRequestModel);
        verify(itemRepository).saveAndFlush(itemArgumentCaptor.capture());
        Item captureditem = itemArgumentCaptor.getValue();
        assertEquals(captureditem.getTitle(), itemRequestModel.getTitle());
        assertEquals(captureditem.getPriceCents(), itemRequestModel.getPriceCents());
        assertEquals(itemRequestModel.getTitle(), itemReturnModel.getResult().getTitle());
        assertNull(itemRequestModel.getSubItems());
        assertEquals(captureditem.getSubItems().size(), 0);
        verify(entityConverter).convertItemRequestModelToItem(itemRequestModel);
        verify(entityConverter).convertItemToReturnModel(item);
        verify(itemValidator).validateItemRequest(uuid, 1L);
        verify(providerRepository).getById(1L);
        verify(itemRepository).saveAndFlush(any());
    }
    
    @Test
    void insertItem_validatorException() {
        ItemRequestModel itemRequestModel = createItemRequestModel_1SubItem();
        doThrow(new BadRequestException(null)).when(itemValidator).validateItemRequest(uuid, 1L);
        assertThrows(BadRequestException.class,
        () -> itemServiceImpl.save(uuid, 1L, itemRequestModel)
        );
        verify(itemValidator).validateItemRequest(uuid, 1L);
        verifyNoInteractions(entityConverter);
        verifyNoInteractions(providerRepository, itemRepository);
    }

}
