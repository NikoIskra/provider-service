package com.provider.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.provider.exception.NoContentException;
import org.junit.jupiter.api.Test;

public class TitleValidatorTest {

  private final TitleValidator titleValidator = new TitleValidator();

  @Test
  void testValidateTitleGetRequest() {
    assertDoesNotThrow(() -> titleValidator.validateTitleGetRequest("test"));
  }

  @Test
  void testValidateTitleGetRequest_noContent() {
    assertThrows(NoContentException.class, () -> titleValidator.validateTitleGetRequest("tes"));
  }
}
