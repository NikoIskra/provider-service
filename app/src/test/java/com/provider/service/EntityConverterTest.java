package com.provider.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.provider.config.Configuration;
import com.provider.model.GetAllProvidersProviderModel;
import com.provider.model.ItemGetReturnModel;
import com.provider.model.ItemPostRequestModel;
import com.provider.model.ItemPostReturnModel;
import com.provider.model.ItemPostSubItemModel;
import com.provider.model.ItemUpdateRequestModel;
import com.provider.model.ItemUpdateReturnModel;
import com.provider.model.ProviderPostRequestModel;
import com.provider.model.ProviderPostReturnModel;
import com.provider.model.ProviderPostReturnModelResult;
import com.provider.model.ProviderUpdateRequestModel;
import com.provider.model.StatusEnum;
import com.provider.model.SubItemPostRequestModel;
import com.provider.model.TitleGetModel;
import com.provider.persistence.entity.Item;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.entity.SubItem;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class EntityConverterTest {

  private final EntityConverterService entityConverterService =
      new EntityConverterService(
          new Configuration().strictModelMapper(), new Configuration().modelMapper());

  private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

  private static ProviderPostRequestModel createProviderRequestModel() {
    ProviderPostRequestModel providerRequestModel =
        new ProviderPostRequestModel("name", "title", "1234567890");
    return providerRequestModel;
  }

  private static Provider createProvider() {
    Provider provider = new Provider(uuid, "name", "title", "1234567890", StatusEnum.VIEW_ONLY);
    Item item = createItem();
    List<Item> items = List.of(item);
    provider.setItems(items);
    return provider;
  }

  private static ProviderPostReturnModelResult createProviderReturnModelResult() {
    ProviderPostReturnModelResult providerReturnModelResult =
        new ProviderPostReturnModelResult()
            .id(1L)
            .name("name")
            .ownerId(uuid)
            .status(StatusEnum.VIEW_ONLY)
            .title("title")
            .phoneNumber("1234567890");
    return providerReturnModelResult;
  }

  private static SubItemPostRequestModel createSubItemRequestModel() {
    SubItemPostRequestModel subItemRequestModel = new SubItemPostRequestModel("subitemtitle", 1300);
    return subItemRequestModel;
  }

  private static SubItem createSubItem() {
    return new SubItem("subitemtitle", 1300, StatusEnum.VIEW_ONLY, null);
  }

  private static ItemPostSubItemModel createSubItemReturnModel() {
    ItemPostSubItemModel subItemReturnModel =
        new ItemPostSubItemModel()
            .title("subitemtitle")
            .priceCents(1300)
            .status(StatusEnum.VIEW_ONLY);
    return subItemReturnModel;
  }

  private static Item createItem() {
    Item item = new Item("itemtitle", 1200, StatusEnum.VIEW_ONLY, null, null);
    item.setDescription("desc");
    return item;
  }

  private static ItemPostRequestModel createItemRequestModel() {
    ItemPostRequestModel itemRequestModel = new ItemPostRequestModel("itemtitle", 1200);
    return itemRequestModel;
  }

  private static ProviderUpdateRequestModel createProviderUpdateRequestModel() {
    ProviderUpdateRequestModel providerUpdateRequestModel =
        new ProviderUpdateRequestModel()
            .description("updatedesc")
            .status(StatusEnum.ACTIVE)
            .title("updatedTitle")
            .phoneNumber("121212121212");
    return providerUpdateRequestModel;
  }

  private static ItemUpdateRequestModel createItemUpdateRequestModel_nullDesc() {
    ItemUpdateRequestModel itemUpdateRequestModel =
        new ItemUpdateRequestModel()
            .description(null)
            .status(StatusEnum.ACTIVE)
            .priceCents(100)
            .title("updatedTitle");
    return itemUpdateRequestModel;
  }

  private static Item createItemWithProviderAndSubItems() {
    Provider provider = createProvider();
    SubItem subItem = createSubItem();
    List<SubItem> subItems = List.of(subItem);
    Item item = createItem();
    item.setProvider(provider);
    item.setSubItems(subItems);
    return item;
  }

  private static List<Object[]> createListOfObjects() {
    Object[] object1 = new Object[] {1, "providertitle", "provider", "path"};
    Object[] object2 = new Object[] {2, "itemTitle", "item", "path"};
    Object[] object3 = new Object[] {3, "subItemTitle", "subitem", "path"};
    List<Object[]> objects = List.of(object2, object1, object3);
    return objects;
  }

  private final List<StatusEnum> statusList =
      List.of(StatusEnum.ACTIVE, StatusEnum.SUSPENDED, StatusEnum.VIEW_ONLY);

  @Test
  void testConvertProviderToReturnModel() {
    Provider provider = createProvider();
    ProviderPostReturnModel returnModel =
        entityConverterService.convertProviderToReturnModel(provider);
    assertEquals(returnModel.isOk(), true);
    assertEquals(returnModel.getResult().getOwnerId(), provider.getOwnerId());
    assertEquals(returnModel.getResult().getName(), provider.getName());
    assertEquals(returnModel.getResult().getTitle(), provider.getTitle());
    assertEquals(returnModel.getResult().getPhoneNumber(), provider.getPhoneNumber());
    assertEquals(returnModel.getResult().getStatus(), provider.getStatus());
  }

  @Test
  void testConvertProviderRequestModelToProvider() {
    ProviderPostRequestModel providerRequestModel = createProviderRequestModel();
    Provider provider =
        entityConverterService.convertProviderRequestModelToProvider(providerRequestModel);
    assertEquals(provider.getStatus(), StatusEnum.VIEW_ONLY);
    assertEquals(provider.getName(), providerRequestModel.getName());
    assertEquals(provider.getTitle(), providerRequestModel.getTitle());
    assertEquals(provider.getPhoneNumber(), providerRequestModel.getPhoneNumber());
  }

  @Test
  void testConvertSubItemRequestModelToSubItem() {
    SubItemPostRequestModel subItemRequestModel = createSubItemRequestModel();
    SubItem subItem =
        entityConverterService.convertSubItemRequestModelToSubItem(subItemRequestModel);
    assertEquals(subItemRequestModel.getTitle(), subItem.getTitle());
    assertEquals(subItemRequestModel.getDescription(), subItem.getDescription());
    assertEquals(subItemRequestModel.getPriceCents(), subItem.getPriceCents());
    assertEquals(subItem.getStatus(), StatusEnum.VIEW_ONLY);
  }

  @Test
  void testConvertSubItemToReturnModel() {
    SubItem subItem = createSubItem();
    ItemPostSubItemModel returnModel = entityConverterService.convertSubItemToReturnModel(subItem);
    assertEquals(returnModel.getTitle(), subItem.getTitle());
    assertEquals(returnModel.getPriceCents(), subItem.getPriceCents());
    assertEquals(returnModel.getStatus(), StatusEnum.VIEW_ONLY);
  }

  @Test
  void testConvertItemRequestModelToItem() {
    ItemPostRequestModel itemRequestModel = createItemRequestModel();
    Item returnItem = entityConverterService.convertItemRequestModelToItem(itemRequestModel);
    assertEquals(itemRequestModel.getTitle(), returnItem.getTitle());
    assertEquals(itemRequestModel.getPriceCents(), returnItem.getPriceCents());
    assertEquals(StatusEnum.VIEW_ONLY, returnItem.getStatus());
  }

  @Test
  void testConvertItemToReturnModel() {
    Item item = createItem();
    ItemPostReturnModel itemReturnModel = entityConverterService.convertItemToReturnModel(item);
    assertEquals(itemReturnModel.isOk(), true);
    assertEquals(itemReturnModel.getResult().getTitle(), item.getTitle());
    assertEquals(itemReturnModel.getResult().getPriceCents(), item.getPriceCents());
    assertEquals(itemReturnModel.getResult().getStatus(), item.getStatus());
  }

  @Test
  void testPatchRequestModelToProvider() {
    Provider provider = createProvider();
    Provider updatedProvider = createProvider();
    ProviderUpdateRequestModel providerUpdateRequestModel = createProviderUpdateRequestModel();
    entityConverterService.patchRequestModelToProvider(providerUpdateRequestModel, updatedProvider);
    assertEquals(provider.getOwnerId(), updatedProvider.getOwnerId());
    assertEquals(provider.getName(), updatedProvider.getName());
    assertEquals(providerUpdateRequestModel.getTitle(), updatedProvider.getTitle());
    assertEquals(providerUpdateRequestModel.getDescription(), updatedProvider.getDescription());
    assertEquals(providerUpdateRequestModel.getStatus(), updatedProvider.getStatus());
    assertEquals(providerUpdateRequestModel.getPhoneNumber(), updatedProvider.getPhoneNumber());
  }

  @Test
  void testUpdateItem() {
    ItemUpdateRequestModel itemUpdateRequestModel = createItemUpdateRequestModel_nullDesc();
    Item item = createItem();
    entityConverterService.updateItemUpdateModelToItem(itemUpdateRequestModel, item);
    assertEquals(itemUpdateRequestModel.getDescription(), item.getDescription());
    assertEquals(itemUpdateRequestModel.getStatus(), item.getStatus());
    assertEquals(itemUpdateRequestModel.getPriceCents(), item.getPriceCents());
    assertEquals(itemUpdateRequestModel.getTitle(), item.getTitle());
  }

  @Test
  void testConvertItemToUpdateReturnModel() {
    Item item = createItem();
    ItemUpdateReturnModel itemUpdateReturnModel =
        entityConverterService.convertItemToUpdateReturnModel(item);
    assertEquals(itemUpdateReturnModel.isOk(), true);
    assertEquals(itemUpdateReturnModel.getResult().getTitle(), item.getTitle());
    assertEquals(itemUpdateReturnModel.getResult().getPriceCents(), item.getPriceCents());
    assertEquals(itemUpdateReturnModel.getResult().getStatus(), item.getStatus());
    assertEquals(itemUpdateReturnModel.getResult().getDescription(), item.getDescription());
  }

  @Test
  void testConvertProviderToGetDataObject() {
    Provider provider = createProvider();
    List<Provider> providers = List.of(provider);
    List<GetAllProvidersProviderModel> providerGetDataObjects = new ArrayList<>();
    entityConverterService.convertProviderToGetDataObjects(
        providerGetDataObjects, providers, statusList);
    GetAllProvidersProviderModel providerGetDataObject = providerGetDataObjects.get(0);
    assertEquals(providerGetDataObject.getName(), provider.getName());
    assertEquals(providerGetDataObject.getPhoneNumber(), provider.getPhoneNumber());
    assertEquals(providerGetDataObject.getTitle(), provider.getTitle());
    assertEquals(providerGetDataObject.getStatus(), provider.getStatus());
  }

  @Test
  void testConvertItemToGetReturnModel() {
    Item item = createItemWithProviderAndSubItems();
    List<SubItem> subItems = new ArrayList<>(item.getSubItems());
    item.setSubItems(subItems);
    ItemGetReturnModel itemGetReturnModel =
        entityConverterService.convertItemToGetReturnModle(item);
    assertEquals(itemGetReturnModel.isOk(), true);
    assertEquals(itemGetReturnModel.getResult().getId(), item.getId());
    assertEquals(
        itemGetReturnModel.getResult().getSubItems().get(0).getId(),
        item.getSubItems().get(0).getId());
    assertEquals(itemGetReturnModel.getResult().getProvider().getId(), item.getProvider().getId());
  }

  @Test
  void testConvertObjectsListToTitleGetModel() {
    List<Object[]> objects = createListOfObjects();
    List<TitleGetModel> titleGetModels =
        entityConverterService.convertObjectsListToTitleGetModel(objects);
    assertEquals(titleGetModels.get(0).getId().intValue(), objects.get(1)[0]);
    assertEquals(titleGetModels.get(0).getTitle(), objects.get(1)[1]);
    assertEquals(titleGetModels.get(0).getType(), objects.get(1)[2]);
    assertEquals(titleGetModels.get(0).getRef(), objects.get(1)[3]);
    assertEquals(titleGetModels.get(1).getId().intValue(), objects.get(0)[0]);
    assertEquals(titleGetModels.get(1).getTitle(), objects.get(0)[1]);
    assertEquals(titleGetModels.get(1).getType(), objects.get(0)[2]);
    assertEquals(titleGetModels.get(1).getRef(), objects.get(0)[3]);
  }
}
