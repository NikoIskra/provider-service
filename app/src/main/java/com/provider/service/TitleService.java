package com.provider.service;

import com.provider.model.OrderByEnum;
import com.provider.model.OrderEnum;
import com.provider.model.TitleGetReturnModel;
import java.util.UUID;

public interface TitleService {
  TitleGetReturnModel getAllByTitle(
      UUID accountID,
      String query,
      OrderByEnum orderByEnum,
      OrderEnum orderEnum,
      Integer page,
      Integer pageSize);
}
