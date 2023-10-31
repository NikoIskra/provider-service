package com.provider.service;

import com.provider.model.GetAllProvidersProviderModel;
import com.provider.model.ItemGetReturnModel;
import com.provider.model.ItemGetReturnModelResult;
import com.provider.model.ItemPostRequestModel;
import com.provider.model.ItemPostReturnModel;
import com.provider.model.ItemPostReturnModelResult;
import com.provider.model.ItemPostSubItemModel;
import com.provider.model.ItemUpdateRequestModel;
import com.provider.model.ItemUpdateReturnModel;
import com.provider.model.ItemUpdateReturnModelResult;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EntityConverterService {

  private final ModelMapper modelMapper;
  private final ModelMapper strictModelMapper;

  public EntityConverterService(
      @Qualifier("strictModelMapper") ModelMapper strictModelMapper,
      @Qualifier("modelMapper") ModelMapper modelMapper) {
    this.strictModelMapper = strictModelMapper;
    this.modelMapper = modelMapper;
  }

  public ProviderPostReturnModel convertProviderToReturnModel(Provider provider) {
    ProviderPostReturnModelResult providerReturnModelResult =
        modelMapper.map(provider, ProviderPostReturnModelResult.class);
    return new ProviderPostReturnModel().ok(true).result(providerReturnModelResult);
  }

  public Provider convertProviderRequestModelToProvider(
      ProviderPostRequestModel providerRequestModel) {
    Provider provider = modelMapper.map(providerRequestModel, Provider.class);
    provider.setStatus(StatusEnum.VIEW_ONLY);
    return provider;
  }

  public SubItem convertSubItemRequestModelToSubItem(SubItemPostRequestModel subItemRequestModel) {
    SubItem subItem = modelMapper.map(subItemRequestModel, SubItem.class);
    subItem.setStatus(StatusEnum.VIEW_ONLY);
    return subItem;
  }

  public ItemPostSubItemModel convertSubItemToReturnModel(SubItem subItem) {
    return modelMapper.map(subItem, ItemPostSubItemModel.class);
  }

  public Item convertItemRequestModelToItem(ItemPostRequestModel itemRequestModel) {
    Item item = modelMapper.map(itemRequestModel, Item.class);
    item.setStatus(StatusEnum.VIEW_ONLY);
    return item;
  }

  public ItemPostReturnModel convertItemToReturnModel(Item item) {
    ItemPostReturnModelResult itemReturnModelResult =
        modelMapper.map(item, ItemPostReturnModelResult.class);
    return new ItemPostReturnModel().ok(true).result(itemReturnModelResult);
  }

  public void patchRequestModelToProvider(
      ProviderUpdateRequestModel providerUpdateRequestModel, Provider provider) {
    modelMapper.map(providerUpdateRequestModel, provider);
  }

  public void updateItemUpdateModelToItem(
      ItemUpdateRequestModel itemUpdateRequestModel, Item item) {
    strictModelMapper.map(itemUpdateRequestModel, item);
  }

  public ItemUpdateReturnModel convertItemToUpdateReturnModel(Item item) {
    ItemUpdateReturnModelResult itemUpdateReturnModelResult =
        modelMapper.map(item, ItemUpdateReturnModelResult.class);
    return new ItemUpdateReturnModel().ok(true).result(itemUpdateReturnModelResult);
  }

  public void convertProviderToGetDataObjects(
      List<GetAllProvidersProviderModel> providerGetDataObjects,
      List<Provider> providers,
      List<StatusEnum> statusList) {
    for (Provider provider : providers) {
      GetAllProvidersProviderModel providerGetDataObject =
          modelMapper.map(provider, GetAllProvidersProviderModel.class);
      List<String> services =
          provider.getItems().stream().map(Item::getTitle).limit(10).collect(Collectors.toList());
      providerGetDataObject.setServices(services);
      providerGetDataObjects.add(providerGetDataObject);
    }
  }

  public ItemGetReturnModel convertItemToGetReturnModle(Item item) {
    Collections.sort(item.getSubItems(), Comparator.comparing(SubItem::getCreatedAt));
    ItemGetReturnModelResult itemGetReturnModelResult =
        modelMapper.map(item, ItemGetReturnModelResult.class);
    return new ItemGetReturnModel().ok(true).result(itemGetReturnModelResult);
  }

  public List<TitleGetModel> convertObjectsListToTitleGetModel(List<Object[]> objects) {
    List<TitleGetModel> titleGetModels = new ArrayList<>();
    for (Object[] row : objects) {
      Long id = ((Number) row[0]).longValue();
      String title = (String) row[1];
      String type = (String) row[2];
      String ref = (String) row[3];
      TitleGetModel titleGetModel = new TitleGetModel().id(id).title(title).type(type).ref(ref);
      titleGetModels.add(titleGetModel);
    }
    Collections.sort(
        titleGetModels, Comparator.comparingInt(model -> getTypeOrder(model.getType())));
    return titleGetModels;
  }

  private int getTypeOrder(String type) {
    switch (type) {
      case "provider":
        return 1;
      case "item":
        return 2;
      case "subitem":
        return 3;
      default:
        return 10;
    }
  }
}
