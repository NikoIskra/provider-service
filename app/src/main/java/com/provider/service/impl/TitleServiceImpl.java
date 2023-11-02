package com.provider.service.impl;

import com.provider.model.OrderByEnum;
import com.provider.model.OrderEnum;
import com.provider.model.TitleGetModel;
import com.provider.model.TitleGetReturnModel;
import com.provider.model.TitleGetReturnModelResult;
import com.provider.service.EntityConverterService;
import com.provider.service.TitleService;
import com.provider.service.TitleValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TitleServiceImpl implements TitleService {

  private final EntityManager entityManager;

  private final EntityConverterService entityConverterService;

  private final TitleValidator titleValidator;

  private static final String queryString =
      "select id, title, type, :orderBy FROM ( select id, title, '1' as type, :orderBy from \"provider-service\".provider WHERE title LIKE :query AND name LIKE :query UNION "
          + "select id, title, '2' as type, :orderBy from \"provider-service\".item WHERE title LIKE :query UNION "
          + "select id, title, '3' as type, :orderBy from \"provider-service\".sub_item WHERE title LIKE :query) AS all_records "
          + "ORDER BY type, :orderBy";

  @Value("${page.size.default}")
  private Integer defaultPageSize;

  @Override
  public TitleGetReturnModel getAllByTitle(
      UUID accountID,
      String query,
      OrderByEnum orderByEnum,
      OrderEnum orderEnum,
      Integer page,
      Integer pageSize) {
    titleValidator.validateTitleGetRequest(query);
    Integer pageSizeActual = Objects.requireNonNullElse(pageSize, defaultPageSize);
    OrderByEnum orderByEnumActual = Objects.requireNonNullElse(orderByEnum, OrderByEnum.CREATED_AT);
    OrderEnum orderEnumActual = Objects.requireNonNullElse(orderEnum, OrderEnum.ASC);
    Query queryExecute = entityManager.createNativeQuery(queryString + orderEnumActual.toString());
    queryExecute.setParameter("query", "%" + query + "%");
    queryExecute.setParameter("orderBy", orderByEnumActual.toString());
    queryExecute.setFirstResult(page * pageSizeActual);
    queryExecute.setMaxResults(pageSizeActual);
    List<Object[]> objects = queryExecute.getResultList();
    List<TitleGetModel> titleGetModels =
        entityConverterService.convertObjectsListToTitleGetModel(objects);
    TitleGetReturnModelResult titleGetReturnModelResult =
        new TitleGetReturnModelResult()
            .data(titleGetModels)
            .page(page)
            .pageSize(pageSizeActual)
            .numberOfPages((int) Math.ceil(titleGetModels.size() / pageSizeActual))
            .query(query)
            .order(orderEnumActual.getValue())
            .orderBy(orderByEnumActual.getValue());

    return new TitleGetReturnModel().ok(true).result(titleGetReturnModelResult);
  }
}
