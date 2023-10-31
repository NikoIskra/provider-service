package com.provider.controller.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.provider.exception.BadRequestException;
import com.provider.model.TitleGetModel;
import com.provider.model.TitleGetReturnModel;
import com.provider.model.TitleGetReturnModelResult;
import com.provider.service.impl.TitleServiceImpl;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@WebMvcTest(TitleController.class)
@EnableWebMvc
public class TitleControllerTest {

  @Autowired MockMvc mvc;

  @MockBean TitleServiceImpl titleServiceImpl;

  private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

  private static TitleGetModel createTitleGetModel() {
    TitleGetModel titleGetModel =
        new TitleGetModel().id(1L).title("testtitle").type("provider").ref("link");
    return titleGetModel;
  }

  private static TitleGetReturnModel createTitleGetReturnModel() {
    TitleGetModel titleGetModel = createTitleGetModel();
    TitleGetModel titleGetModel2 = createTitleGetModel();
    List<TitleGetModel> titleGetModels = List.of(titleGetModel, titleGetModel2);
    TitleGetReturnModelResult titleGetReturnModelResult =
        new TitleGetReturnModelResult()
            .data(titleGetModels)
            .page(0)
            .pageSize(50)
            .numberOfPages(2)
            .query("test")
            .order("asc")
            .orderBy("createdAt");
    return new TitleGetReturnModel().ok(true).result(titleGetReturnModelResult);
  }

  @Test
  void testGetByTitle() throws Exception {
    TitleGetReturnModel titleGetReturnModel = createTitleGetReturnModel();
    when(titleServiceImpl.getAllByTitle(any(), any(), any(), any(), any(), any()))
        .thenReturn(titleGetReturnModel);
    mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/title/search?query=test&page=0")
                .header("X-ACCOUNT-ID", uuid.toString()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.result.page").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.result.data[0].id").value(1));
  }

  @Test
  void testGetByTitle_badRequest() throws Exception {
    TitleGetReturnModel titleGetReturnModel = createTitleGetReturnModel();
    when(titleServiceImpl.getAllByTitle(any(), any(), any(), any(), any(), any()))
        .thenThrow(new BadRequestException("bad"));
    mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/title/search?query=test&page=0")
                .header("X-ACCOUNT-ID", uuid.toString()))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value(false))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("bad"));
  }
}
