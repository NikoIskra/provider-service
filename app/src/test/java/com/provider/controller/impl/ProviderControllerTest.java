package com.provider.controller.impl;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.provider.exception.BadRequestException;
import com.provider.model.GetAllProvidersProviderModel;
import com.provider.model.ProviderGetAllReturnModel;
import com.provider.model.ProviderGetAllReturnModelResult;
import com.provider.model.ProviderPostRequestModel;
import com.provider.model.ProviderPostReturnModel;
import com.provider.model.ProviderPostReturnModelResult;
import com.provider.model.ProviderUpdateRequestModel;
import com.provider.model.StatusEnum;
import com.provider.service.impl.ProviderServiceImpl;
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

@WebMvcTest(ProviderController.class)
@EnableWebMvc
public class ProviderControllerTest {
  @Autowired MockMvc mvc;

  @MockBean ProviderServiceImpl providerServiceImpl;

  private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

  private static ProviderPostRequestModel createProviderRequestModel() {
    ProviderPostRequestModel providerRequestModel =
        new ProviderPostRequestModel("testname", "testtitle", "1234567890");
    return providerRequestModel;
  }

  private static ProviderPostRequestModel createInvalidProviderRequestModel() {
    ProviderPostRequestModel providerRequestModel =
    new ProviderPostRequestModel("testname", "testtitle", "12345678");
    return providerRequestModel;
  }

  private static ProviderPostReturnModel createProviderReturnModel() {
    ProviderPostReturnModelResult providerReturnModelResult =
        new ProviderPostReturnModelResult()
            .id(1L)
            .phoneNumber("1324567890")
            .ownerId(uuid)
            .name("testname")
            .title("testtitle")
            .status(StatusEnum.VIEW_ONLY);
    ProviderPostReturnModel providerReturnModel =
        new ProviderPostReturnModel().ok(true).result(providerReturnModelResult);
    return providerReturnModel;
  }

  private static ProviderUpdateRequestModel createProviderUpdateRequestModel() {
    ProviderUpdateRequestModel providerUpdateRequestModel =
        new ProviderUpdateRequestModel()
            .description("updatedesc")
            .status(StatusEnum.ACTIVE)
            .title("updatedTitle");
    return providerUpdateRequestModel;
  }

  private static ProviderUpdateRequestModel createInvalidProviderUpdateRequestModel() {
    ProviderUpdateRequestModel providerUpdateRequestModel =
        new ProviderUpdateRequestModel()
            .description("updatedesc")
            .status(StatusEnum.ACTIVE)
            .phoneNumber("1234")
            .title("updatedTitle");
    return providerUpdateRequestModel;
  }

  private static ProviderPostReturnModel createPatchedProviderReturnModel() {
    ProviderPostReturnModelResult providerReturnModelResult =
        new ProviderPostReturnModelResult()
            .id(1L)
            .phoneNumber("1324567890")
            .ownerId(uuid)
            .description("updatedesc")
            .name("testname")
            .title("updatedTitle")
            .status(StatusEnum.ACTIVE);
    ProviderPostReturnModel providerReturnModel =
        new ProviderPostReturnModel().ok(true).result(providerReturnModelResult);
    return providerReturnModel;
  }

  private static ProviderGetAllReturnModel createProviderGetAllReturnModel() {
    List<String> services = List.of("test1", "test2");
    GetAllProvidersProviderModel providerGetDataObject =
        new GetAllProvidersProviderModel().id(1L).description("testdesc").services(services);
    List<GetAllProvidersProviderModel> providerDataList = List.of(providerGetDataObject);
    ProviderGetAllReturnModelResult providerGetAllReturnModelResult =
        new ProviderGetAllReturnModelResult()
            .data(providerDataList)
            .numberOfPages(2)
            .page(0)
            .pageSize(50);
    return new ProviderGetAllReturnModel().ok(true).result(providerGetAllReturnModelResult);
  }

  ObjectMapper mapper = new ObjectMapper();

  @Test
  void insertProvider() throws Exception {
    ProviderPostRequestModel providerRequestModel = createProviderRequestModel();
    ProviderPostReturnModel providerReturnModel = createProviderReturnModel();
    when(providerServiceImpl.save(uuid, providerRequestModel)).thenReturn(providerReturnModel);
    mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/provider")
                .header("X-ACCOUNT-ID", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(providerRequestModel)))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(true))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.result.id")
                .value(providerReturnModel.getResult().getId()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.result.phoneNumber")
                .value(providerReturnModel.getResult().getPhoneNumber()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.result.name")
                .value(providerReturnModel.getResult().getName()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.result.title")
                .value(providerReturnModel.getResult().getTitle()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.result.status")
                .value(providerReturnModel.getResult().getStatus().toString()));
  }

  @Test
  void insertProviderBadRequest() throws Exception {
    ProviderPostRequestModel providerRequestModel = createInvalidProviderRequestModel();
    mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/provider")
                .header("X-ACCOUNT-ID", "d3d35b67-b8f9-464b-a0b5-39f526e1f5f2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(providerRequestModel)))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(false))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("validation failed"));
  }

  @Test
  void patchProvider() throws Exception {
    ProviderUpdateRequestModel providerUpdateRequestModel = createProviderUpdateRequestModel();
    ProviderPostReturnModel providerReturnModel = createPatchedProviderReturnModel();
    when(providerServiceImpl.patch(uuid, 1L, providerUpdateRequestModel))
        .thenReturn(providerReturnModel);
    mvc.perform(
            MockMvcRequestBuilders.patch("/api/v1/provider/1")
                .header("X-ACCOUNT-ID", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(providerUpdateRequestModel)))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(true))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.result.id")
                .value(providerReturnModel.getResult().getId()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.result.phoneNumber")
                .value(providerReturnModel.getResult().getPhoneNumber()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.result.name")
                .value(providerReturnModel.getResult().getName()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.result.title")
                .value(providerReturnModel.getResult().getTitle()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.result.status")
                .value(providerReturnModel.getResult().getStatus().toString()));
  }

  @Test
  void patchProvider_BadRequest() throws Exception {
    ProviderUpdateRequestModel providerUpdateRequestModel =
        createInvalidProviderUpdateRequestModel();
    mvc.perform(
            MockMvcRequestBuilders.patch("/api/v1/provider/12")
                .header("X-ACCOUNT-ID", "d3d35b67-b8f9-464b-a0b5-39f526e1f5f2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(providerUpdateRequestModel)))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(false))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("validation failed"));
  }

  @Test
  void getProviders() throws Exception {
    ProviderGetAllReturnModel providerGetAllReturnModel = createProviderGetAllReturnModel();
    when(providerServiceImpl.getAll(uuid, 0, null)).thenReturn(providerGetAllReturnModel);
    mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/provider?page=0")
                .header("X-ACCOUNT-ID", uuid.toString()))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.result.numberOfPages").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$.result.page").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.result.pageSize").value(50));
  }

  @Test
  void getProviders_badRequest() throws Exception {
    when(providerServiceImpl.getAll(uuid, 0, null)).thenThrow(new BadRequestException("failed"));
    mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/provider?page=0")
                .header("X-ACCOUNT-ID", uuid.toString()))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(false))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("failed"));
  }
}
