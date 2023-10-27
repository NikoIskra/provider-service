package com.provider.controller.impl;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.provider.exception.BadRequestException;
import com.provider.exception.NotFoundException;
import com.provider.model.ItemGetReturnModel;
import com.provider.model.ItemGetReturnModelResult;
import com.provider.model.ItemRequestModel;
import com.provider.model.ItemReturnModel;
import com.provider.model.ItemReturnModelResult;
import com.provider.model.ItemSubItemsModel;
import com.provider.model.ItemUpdateRequestModel;
import com.provider.model.ItemUpdateReturnModel;
import com.provider.model.ItemUpdateReturnModelResult;
import com.provider.model.ProviderSchema;
import com.provider.model.StatusEnum;
import com.provider.model.SubItemRequestModel;
import com.provider.model.SubItemReturnModel;
import com.provider.persistence.repository.ProviderRepository;
import com.provider.service.impl.ItemServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@WebMvcTest(ItemController.class)
@EnableWebMvc
public class ItemControllerTest {
  @Autowired MockMvc mvc;

  @MockBean ProviderRepository providerRepository;

  @MockBean ItemServiceImpl itemServiceImpl;

  private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

  ObjectMapper mapper = new ObjectMapper();

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
    SubItemReturnModel subItemReturnModel =
        new SubItemReturnModel()
            .id(1L)
            .itemId(2L)
            .title("testtitle")
            .description("desc")
            .priceCents(140)
            .status(StatusEnum.VIEW_ONLY);
    subItemReturnModels.add(subItemReturnModel);
    ItemReturnModelResult itemReturnModelResult =
        new ItemReturnModelResult()
            .id(1L)
            .providerId(1000L)
            .title("itemtitle")
            .priceCents(1400)
            .status(StatusEnum.VIEW_ONLY)
            .subItems(subItemReturnModels);
    return new ItemReturnModel().ok(true).result(itemReturnModelResult);
  }

  private static ItemUpdateRequestModel createItemUpdateRequestModel() {
    ItemUpdateRequestModel itemUpdateRequestModel =
        new ItemUpdateRequestModel("updatedtitle", 100, StatusEnum.ACTIVE);
    itemUpdateRequestModel.setDescription("updatedDesc");
    return itemUpdateRequestModel;
  }

  private static ItemUpdateReturnModel createItemUpdateReturnModel() {
    ItemUpdateReturnModelResult itemUpdateReturnModelResult =
        new ItemUpdateReturnModelResult()
            .description("updatedDesc")
            .title("updatedtitle")
            .priceCents(100)
            .status(StatusEnum.ACTIVE)
            .id(2L);
    return new ItemUpdateReturnModel().ok(true).result(itemUpdateReturnModelResult);
  }

  private static ProviderSchema createProvider() {
    ProviderSchema provider =
        new ProviderSchema()
            .id(1L)
            .name("testname")
            .title("testtitle")
            .status(StatusEnum.ACTIVE)
            .phoneNumber("123456789");
    return provider;
  }

  private static ItemSubItemsModel createItemSubItemsModel() {
    ItemSubItemsModel itemSubItemsModel =
        new ItemSubItemsModel()
            .id(1L)
            .title("testtitle")
            .description("testdesc")
            .priceCents(123)
            .status(StatusEnum.ACTIVE);
    return itemSubItemsModel;
  }

  private static ItemGetReturnModel createItemGetReturnModel() {
    ProviderSchema provider = createProvider();
    ItemSubItemsModel itemSubItemsModel = createItemSubItemsModel();
    List<ItemSubItemsModel> subItemsModels = List.of(itemSubItemsModel);
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
  void insertItem() throws Exception {
    ItemRequestModel itemRequestModel = createItemRequestModel();
    ItemReturnModel itemReturnModel = createItemReturnModel();
    when(itemServiceImpl.save(uuid, 1L, itemRequestModel)).thenReturn(itemReturnModel);
    mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/provider/1/item")
                .header("X-ACCOUNT-ID", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(itemRequestModel)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value(1));
  }

  @Test
  void insertItem_internalServerError() throws Exception {
    ItemRequestModel itemRequestModel = createItemRequestModel();
    mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/provider/1/item")
                .header("X-ACCOUNT-ID", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(itemRequestModel)))
        .andExpect(MockMvcResultMatchers.status().isInternalServerError())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(false));
  }

  @Test
  void insertItem_badRequest() throws Exception {
    ItemRequestModel itemRequestModel = createItemRequestModel();
    when(itemServiceImpl.save(uuid, 1L, itemRequestModel))
        .thenThrow(new BadRequestException("bad request"));
    mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/provider/1/item")
                .header("X-ACCOUNT-ID", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(itemRequestModel)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(false))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("bad request"));
  }

  @Test
  void updateItem() throws Exception {
    ItemUpdateRequestModel itemUpdateRequestModel = createItemUpdateRequestModel();
    ItemUpdateReturnModel itemUpdateReturnModel = createItemUpdateReturnModel();
    when(itemServiceImpl.put(uuid, 1L, 1L, itemUpdateRequestModel))
        .thenReturn(itemUpdateReturnModel);
    mvc.perform(
            MockMvcRequestBuilders.put("/api/v1/provider/1/item/1")
                .header("X-ACCOUNT-ID", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(itemUpdateRequestModel)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value(2));
  }

  @Test
  void updateItem_badRequest() throws Exception {
    ItemUpdateRequestModel itemUpdateRequestModel = null;
    ItemUpdateReturnModel itemUpdateReturnModel = createItemUpdateReturnModel();
    when(itemServiceImpl.put(uuid, 1L, 1L, itemUpdateRequestModel))
        .thenReturn(itemUpdateReturnModel);
    mvc.perform(
            MockMvcRequestBuilders.put("/api/v1/provider/1/item/1")
                .header("X-ACCOUNT-ID", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(itemUpdateRequestModel)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(false));
  }

  @Test
  void getItem() throws Exception {
    ItemGetReturnModel itemGetReturnModel = createItemGetReturnModel();
    when(itemServiceImpl.get(uuid, 1L, 1L)).thenReturn(itemGetReturnModel);
    mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/provider/1/item/1")
                .header("X-ACCOUNT-ID", uuid.toString()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.result.provider.id").value(1L));
  }

  @Test
  void getItem_notFound() throws Exception {
    when(itemServiceImpl.get(uuid, 1L, 1L)).thenThrow(new NotFoundException("not found"));
    mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/provider/1/item/1")
                .header("X-ACCOUNT-ID", uuid.toString()))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(false))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("not found"));
  }
}
