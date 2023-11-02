package com.provider.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.provider.exception.NoContentException;
import com.provider.model.OrderByEnum;
import com.provider.model.OrderEnum;
import com.provider.model.TitleGetModel;
import com.provider.model.TitleGetReturnModel;
import com.provider.model.TitleGetReturnModelResult;
import com.provider.service.EntityConverterService;
import com.provider.service.TitleValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class titleServiceImplTest {

  @Mock EntityManager entityManager;

  @Mock EntityConverterService entityConverterService;

  @Mock Query query;

  @Mock TitleValidator titleValidator;

  @InjectMocks TitleServiceImpl titleServiceImpl;

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
  void testGet() {
    TitleGetReturnModel titleGetReturnModel = createTitleGetReturnModel();
    List<Object[]> objects = new ArrayList<>();
    Query query = mock(Query.class);
    when(entityManager.createNativeQuery(any())).thenReturn(query);
    when(query.setFirstResult(0)).thenReturn(query);
    when(query.setMaxResults(50)).thenReturn(query);
    when(query.getResultList()).thenReturn(objects);
    when(entityConverterService.convertObjectsListToTitleGetModel(any()))
        .thenReturn(titleGetReturnModel.getResult().getData());
    TitleGetReturnModel titleGetReturnModel2 =
        titleServiceImpl.getAllByTitle(uuid, "test", OrderByEnum.CREATED_AT, OrderEnum.ASC, 0, 50);
    assertEquals(titleGetReturnModel2.isOk(), true);
    assertEquals(titleGetReturnModel2.getResult().getPage(), 0);
    assertEquals(titleGetReturnModel2.getResult().getData().get(0).getId(), 1L);
    verify(titleValidator).validateTitleGetRequest("test");
    verify(entityManager).createNativeQuery(any());
    verify(entityConverterService).convertObjectsListToTitleGetModel(any());
  }

  @Test
  void testGet_validatorException() {
    doThrow(NoContentException.class).when(titleValidator).validateTitleGetRequest("query");
    assertThrows(
        NoContentException.class,
        () ->
            titleServiceImpl.getAllByTitle(
                uuid, "query", OrderByEnum.CREATED_AT, OrderEnum.ASC, 0, 50));
    verify(titleValidator).validateTitleGetRequest("query");
    verifyNoInteractions(entityManager, entityConverterService);
  }
}
