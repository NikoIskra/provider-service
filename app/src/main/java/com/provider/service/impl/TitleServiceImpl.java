package com.provider.service.impl;

import com.provider.model.OrderByEnum;
import com.provider.model.OrderEnum;
import com.provider.model.TitleGetModel;
import com.provider.model.TitleGetReturnModel;
import com.provider.model.TitleGetReturnModelResult;
import com.provider.service.EntityConverterService;
import com.provider.service.TitleService;
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
    Integer pageSizeActual = Objects.requireNonNullElse(pageSize, defaultPageSize);
    OrderByEnum orderByEnumActual = Objects.requireNonNullElse(orderByEnum, OrderByEnum.CREATED_AT);
    OrderEnum orderEnumActual = Objects.requireNonNullElse(orderEnum, OrderEnum.ASC);
    String databaseQuery =
        "select id, title, 'provider' as type, CONCAT('/api/v1/provider/', CAST(id as CHAR)) as ref, created_at from \"provider-service\".provider WHERE title LIKE '%"
            + query
            + "%' AND name LIKE '%"
            + query
            + "%' UNION "
            + "select id, title, 'item' as type, CONCAT(CONCAT('/api/v1/provider/', CAST(provider_id as CHAR)), CONCAT('/item/', CAST(id as CHAR))), created_at as ref from \"provider-service\".item WHERE title LIKE '%"
            + query
            + "%' UNION "
            + "select id, title, 'subitem' as type, CONCAT(CONCAT('/api/v1/item/', CAST(item_id as CHAR)), CONCAT('/subitem/', CAST(id as CHAR))), created_at from \"provider-service\".sub_item WHERE title LIKE '%"
            + query
            + "%' "
            + "ORDER BY "
            + orderByEnumActual.toString()
            + " "
            + orderEnumActual.toString();
    Query queryExecute = entityManager.createNativeQuery(databaseQuery);
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
