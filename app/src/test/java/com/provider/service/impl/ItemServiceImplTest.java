package com.provider.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.provider.exception.BadRequestException;
import com.provider.exception.NotFoundException;
import com.provider.model.GetItemsProviderModel;
import com.provider.model.GetItemsSubItemModel;
import com.provider.model.ItemGetReturnModel;
import com.provider.model.ItemGetReturnModelResult;
import com.provider.model.ItemPostRequestModel;
import com.provider.model.ItemPostReturnModel;
import com.provider.model.ItemPostReturnModelResult;
import com.provider.model.ItemPostSubItemModel;
import com.provider.model.ItemUpdateRequestModel;
import com.provider.model.ItemUpdateReturnModel;
import com.provider.model.ItemUpdateReturnModelResult;
import com.provider.model.StatusEnum;
import com.provider.model.SubItemPostRequestModel;
import com.provider.persistence.entity.Item;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.entity.SubItem;
import com.provider.persistence.repository.ItemRepository;
import com.provider.persistence.repository.ProviderRepository;
import com.provider.service.EntityConverterService;
import com.provider.service.ItemValidator;
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

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
  @Mock ItemRepository itemRepository;

  @Mock ItemValidator itemValidator;

  @Mock ProviderRepository providerRepository;

  @Mock EntityConverterService entityConverter;

  @InjectMocks ItemServiceImpl itemServiceImpl;

  @Captor ArgumentCaptor<Item> itemArgumentCaptor;

  private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

  private static ItemPostRequestModel createItemRequestModel_1SubItem() {
    ItemPostRequestModel itemRequestModel = new ItemPostRequestModel("itemtitle", 1200);
    List<SubItemPostRequestModel> subItemRequestModels = new ArrayList<>();
    SubItemPostRequestModel subItemRequestModel = new SubItemPostRequestModel("subitemtitle", 1300);
    subItemRequestModels.add(subItemRequestModel);
    itemRequestModel.setSubItems(subItemRequestModels);
    return itemRequestModel;
  }

  private static ItemPostRequestModel createItemRequestModel_emptySubItem() {
    ItemPostRequestModel itemRequestModel = new ItemPostRequestModel("itemtitle", 1200);
    List<SubItemPostRequestModel> subItemRequestModels = new ArrayList<>();
    itemRequestModel.setSubItems(subItemRequestModels);
    return itemRequestModel;
  }

  private static ItemPostRequestModel createItemRequestModel_nullSubItem() {
    ItemPostRequestModel itemRequestModel = new ItemPostRequestModel("itemtitle", 1200);
    return itemRequestModel;
  }

  private static SubItem createSubItem() {
    return new SubItem("subitemtitle", 1300, StatusEnum.VIEW_ONLY, null);
  }

  private static Item createItem() {
    Item item = new Item("itemtitle", 1200, StatusEnum.VIEW_ONLY, null, null);
    return item;
  }

  private static ItemPostReturnModel createItemReturnModel() {
    List<ItemPostSubItemModel> subItemReturnModels = new ArrayList<>();
    ItemPostSubItemModel subItemReturnModel =
        new ItemPostSubItemModel()
            .id(1L)
            .itemId(2L)
            .title("subitemtitle")
            .description("desc")
            .priceCents(140)
            .status(StatusEnum.VIEW_ONLY);
    subItemReturnModels.add(subItemReturnModel);
    ItemPostReturnModelResult itemReturnModelResult =
        new ItemPostReturnModelResult()
            .id(1L)
            .providerId(1000L)
            .title("itemtitle")
            .priceCents(1400)
            .status(StatusEnum.VIEW_ONLY)
            .subItems(subItemReturnModels);
    return new ItemPostReturnModel().ok(true).result(itemReturnModelResult);
  }

  private static ItemUpdateRequestModel createItemUpdateRequestModel() {
    ItemUpdateRequestModel itemUpdateRequestModel =
        new ItemUpdateRequestModel("itemtitle", 1200, StatusEnum.VIEW_ONLY);
    return itemUpdateRequestModel;
  }

  private static Provider createProvider() {
    Provider provider =
        new Provider(uuid, "providername", "providertitle", "1234567890", StatusEnum.VIEW_ONLY);
    return provider;
  }

  private static ItemUpdateReturnModel createItemUpdateReturnModel() {
    ItemUpdateReturnModelResult itemUpdateReturnModelResult =
        new ItemUpdateReturnModelResult().description("returndesc").id(1L);
    return new ItemUpdateReturnModel().ok(true).result(itemUpdateReturnModelResult);
  }

  private static GetItemsProviderModel createProviderSchema() {
    GetItemsProviderModel provider =
        new GetItemsProviderModel()
            .id(1L)
            .name("testname")
            .title("testtitle")
            .status(StatusEnum.ACTIVE)
            .phoneNumber("123456789");
    return provider;
  }

  private static GetItemsSubItemModel createItemSubItemsModel() {
    GetItemsSubItemModel itemSubItemsModel =
        new GetItemsSubItemModel()
            .id(1L)
            .title("testtitle")
            .description("testdesc")
            .priceCents(123)
            .status(StatusEnum.ACTIVE);
    return itemSubItemsModel;
  }

  private static ItemGetReturnModel createItemGetReturnModel() {
    GetItemsProviderModel provider = createProviderSchema();
    GetItemsSubItemModel itemSubItemsModel = createItemSubItemsModel();
    List<GetItemsSubItemModel> subItemsModels = List.of(itemSubItemsModel);
    ItemGetReturnModelResult itemGetReturnModelResult =
        new ItemGetReturnModelResult()
            .id(1L)
            .title("testtitle")
            .description("testdesc")
            .priceCents(1200)
            .status(StatusEnum.ACTIVE)
            .provider(provider)
            .subItems(subItemsModels);
    return new ItemGetReturnModel().ok(true).result(itemGetReturnModelResult);
  }

  @Test
  void insertItem() {
    ItemPostRequestModel itemRequestModel = createItemRequestModel_1SubItem();
    doNothing().when(itemValidator).validateItemRequest(uuid, 1L);
    Provider provider = createProvider();
    Item item = createItem();
    ItemPostReturnModel itemReturnModel = createItemReturnModel();
    when(entityConverter.convertItemRequestModelToItem(itemRequestModel)).thenReturn(item);
    when(providerRepository.getById(1L)).thenReturn(provider);
    when(entityConverter.convertItemToReturnModel(item)).thenReturn(itemReturnModel);
    SubItem subItem = createSubItem();
    when(entityConverter.convertSubItemRequestModelToSubItem(any())).thenReturn(subItem);
    ItemPostReturnModel itemReturnModel2 = itemServiceImpl.save(uuid, 1L, itemRequestModel);
    verify(itemRepository).saveAndFlush(itemArgumentCaptor.capture());
    Item captureditem = itemArgumentCaptor.getValue();
    assertEquals(captureditem.getTitle(), itemRequestModel.getTitle());
    assertEquals(captureditem.getPriceCents(), itemRequestModel.getPriceCents());
    assertEquals(captureditem.getSubItems().size(), itemRequestModel.getSubItems().size());
    assertEquals(
        captureditem.getSubItems().get(0).getTitle(),
        itemRequestModel.getSubItems().get(0).getTitle());
    assertEquals(
        captureditem.getSubItems().get(0).getPriceCents(),
        itemRequestModel.getSubItems().get(0).getPriceCents());
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
    ItemPostRequestModel itemRequestModel = createItemRequestModel_emptySubItem();
    doNothing().when(itemValidator).validateItemRequest(uuid, 1L);
    Provider provider = createProvider();
    Item item = createItem();
    ItemPostReturnModel itemReturnModel = createItemReturnModel();
    when(entityConverter.convertItemRequestModelToItem(itemRequestModel)).thenReturn(item);
    when(providerRepository.getById(1L)).thenReturn(provider);
    when(entityConverter.convertItemToReturnModel(item)).thenReturn(itemReturnModel);
    ItemPostReturnModel itemReturnModel2 = itemServiceImpl.save(uuid, 1L, itemRequestModel);
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
    ItemPostRequestModel itemRequestModel = createItemRequestModel_nullSubItem();
    doNothing().when(itemValidator).validateItemRequest(uuid, 1L);
    Provider provider = createProvider();
    Item item = createItem();
    ItemPostReturnModel itemReturnModel = createItemReturnModel();
    when(entityConverter.convertItemRequestModelToItem(itemRequestModel)).thenReturn(item);
    when(providerRepository.getById(1L)).thenReturn(provider);
    when(entityConverter.convertItemToReturnModel(item)).thenReturn(itemReturnModel);
    ItemPostReturnModel itemReturnModel2 = itemServiceImpl.save(uuid, 1L, itemRequestModel);
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
    ItemPostRequestModel itemRequestModel = createItemRequestModel_1SubItem();
    doThrow(new BadRequestException(null)).when(itemValidator).validateItemRequest(uuid, 1L);
    assertThrows(BadRequestException.class, () -> itemServiceImpl.save(uuid, 1L, itemRequestModel));
    verify(itemValidator).validateItemRequest(uuid, 1L);
    verifyNoInteractions(entityConverter);
    verifyNoInteractions(providerRepository, itemRepository);
  }

  @Test
  void updateItem() {
    ItemUpdateRequestModel itemUpdateRequestModel = createItemUpdateRequestModel();
    Item item = createItem();
    ItemUpdateReturnModel itemUpdateReturnModel = createItemUpdateReturnModel();
    when(itemRepository.getById(any())).thenReturn(item);
    when(entityConverter.convertItemToUpdateReturnModel(item)).thenReturn(itemUpdateReturnModel);
    ItemUpdateReturnModel returnModel = itemServiceImpl.put(uuid, 1L, 2L, itemUpdateRequestModel);
    verify(itemRepository).save(itemArgumentCaptor.capture());
    Item captureditem = itemArgumentCaptor.getValue();
    assertEquals(captureditem.getDescription(), itemUpdateRequestModel.getDescription());
    assertEquals(captureditem.getTitle(), itemUpdateRequestModel.getTitle());
    assertEquals(captureditem.getPriceCents(), itemUpdateRequestModel.getPriceCents());
    assertEquals(captureditem.getStatus(), itemUpdateRequestModel.getStatus());
    verify(itemValidator).validateItemPut(uuid, 1L, 2L);
    verify(itemRepository).getById(2L);
    verify(itemRepository).save(any());
    verify(entityConverter).convertItemToUpdateReturnModel(any());
  }

  @Test
  void updateItem_validatorException() {
    ItemUpdateRequestModel itemUpdateRequestModel = createItemUpdateRequestModel();
    doThrow(BadRequestException.class).when(itemValidator).validateItemPut(uuid, 1L, 2L);
    assertThrows(
        BadRequestException.class, () -> itemServiceImpl.put(uuid, 1L, 2L, itemUpdateRequestModel));
    verify(itemValidator).validateItemPut(uuid, 1L, 2L);
    verifyNoInteractions(itemRepository, entityConverter);
  }

  @Test
  void getItem() {
    Item item = createItem();
    ItemGetReturnModel itemGetReturnModel = createItemGetReturnModel();
    when(itemRepository.getItemWithParentAndListOfChildren(anyLong(), anyLong())).thenReturn(item);
    when(entityConverter.convertItemToGetReturnModle(item)).thenReturn(itemGetReturnModel);
    ItemGetReturnModel returnModel = itemServiceImpl.get(uuid, 1L, 1L);
    assertEquals(returnModel.isOk(), itemGetReturnModel.isOk());
    assertEquals(returnModel.getResult().getId(), itemGetReturnModel.getResult().getId());
    assertEquals(
        returnModel.getResult().getSubItems().get(0).getId(),
        itemGetReturnModel.getResult().getSubItems().get(0).getId());
    assertEquals(
        returnModel.getResult().getProvider().getId(),
        itemGetReturnModel.getResult().getProvider().getId());
    verify(itemValidator).validateItemGet(uuid, 1L, 1L);
    verify(itemRepository).getItemWithParentAndListOfChildren(anyLong(), anyLong());
    verify(entityConverter).convertItemToGetReturnModle(item);
  }

  @Test
  void getItem_validatorException() {
    doThrow(NotFoundException.class).when(itemValidator).validateItemGet(uuid, 1L, 1L);
    assertThrows(NotFoundException.class, () -> itemServiceImpl.get(uuid, 1L, 1L));
    verify(itemValidator).validateItemGet(uuid, 1L, 1L);
    verifyNoInteractions(itemRepository, entityConverter);
  }
}
