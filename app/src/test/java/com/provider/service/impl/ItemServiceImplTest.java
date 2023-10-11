package com.provider.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.provider.exception.BadRequestException;
import com.provider.model.ItemRequestModel;
import com.provider.model.ItemReturnModel;
import com.provider.model.ItemReturnModelResult;
import com.provider.model.SubItemRequestModel;
import com.provider.model.SubItemReturnModel;
import com.provider.persistence.entity.Provider;
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
    EntityManager entityManager;

    @Mock
    ProviderRepository providerRepository;

    @InjectMocks
    ItemServiceImpl itemServiceImpl;

    private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

    private static ItemRequestModel createItemRequestModel() {
        ItemRequestModel itemRequestModel = new ItemRequestModel("testtitle", 1200);
        List<SubItemRequestModel> subItemRequestModels = new ArrayList<>();
        SubItemRequestModel subItemRequestModel = new SubItemRequestModel("subitemtitle", 1300);
        subItemRequestModels.add(subItemRequestModel);
        itemRequestModel.setSubItems(subItemRequestModels);
        return itemRequestModel;
    }

    private static ItemReturnModel createItemReturnModel() {
        List<SubItemReturnModel> subItemReturnModels = new ArrayList<>();
        SubItemReturnModel subItemReturnModel = new SubItemReturnModel()
                .id(1L)
                .itemId(2L)
                .title("testtitle")
                .description("desc")
                .priceCents(140)
                .status("view-only");
        subItemReturnModels.add(subItemReturnModel);
        ItemReturnModelResult itemReturnModelResult = new ItemReturnModelResult()
                .id(1L)
                .providerId(1000L)
                .title("itemtitle")
                .priceCents(1400)
                .status("view-only")
                .subItems(subItemReturnModels);
        return new ItemReturnModel().ok(true).result(itemReturnModelResult);
    }

    @Test
    void insertItem() {
        ItemRequestModel itemRequestModel = createItemRequestModel();
        doNothing().when(itemValidator).validateItemRequest(uuid, 1L);
        Provider provider = mock(Provider.class);
        when(providerRepository.getById(1L)).thenReturn(provider);
        ItemReturnModel itemReturnModel = itemServiceImpl.save(uuid, 1L, itemRequestModel);
        assertEquals(itemRequestModel.getTitle(), itemReturnModel.getResult().getTitle());
        verify(itemValidator).validateItemRequest(uuid, 1L);
        verify(entityManager).refresh(any());
        verify(providerRepository).getById(1L);
        verify(itemRepository).saveAndFlush(any());
    }
    
    @Test
    void insertItem_validatorException() {
        ItemRequestModel itemRequestModel = createItemRequestModel();
        doThrow(new BadRequestException(null)).when(itemValidator).validateItemRequest(uuid, 1L);
        assertThrows(BadRequestException.class,
        () -> itemServiceImpl.save(uuid, 1L, itemRequestModel)
        );
        verify(itemValidator).validateItemRequest(uuid, 1L);
        verifyNoInteractions(entityManager);
        verifyNoInteractions(providerRepository);
        verifyNoInteractions(itemRepository);
    }

}
