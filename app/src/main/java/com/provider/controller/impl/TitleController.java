package com.provider.controller.impl;

import com.provider.controller.TitleApi;
import com.provider.model.OrderByEnum;
import com.provider.model.OrderEnum;
import com.provider.model.TitleGetReturnModel;
import com.provider.service.TitleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TitleController implements TitleApi {

  @Autowired private TitleService titleService;

  @Override
  public ResponseEntity<TitleGetReturnModel> getAllByTitle(
      @NotNull UUID X_ACCOUNT_ID,
      @NotNull @Size(min = 3) @Valid String query,
      @NotNull @Valid Integer page,
      @Valid OrderByEnum orderBy,
      @Valid OrderEnum order,
      @Min(20) @Max(100) @Valid Integer pageSize)
      throws Exception {
    TitleGetReturnModel titleGetReturnModel =
        titleService.getAllByTitle(X_ACCOUNT_ID, query, orderBy, order, page, pageSize);
    return ResponseEntity.status(HttpStatus.OK).body(titleGetReturnModel);
  }
}
