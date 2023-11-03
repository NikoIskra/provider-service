package com.provider.service;

import com.provider.exception.NoContentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TitleValidator {

  public void validateTitleGetRequest(String query) {
    if (query.length() <= 3) {
      throw new NoContentException("query is out of range");
    }
  }
}
