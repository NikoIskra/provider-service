package com.provider.model;

import java.util.*;

import org.hibernate.validator.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;

/**
 * Gets or Sets statusEnum
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public enum StatusEnum {

  ACTIVE("active"),

  SUSPENDED("suspended"),

  VIEW_ONLY("view-only"),

  CANCELLED("cancelled");

  private String value;

  StatusEnum(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static StatusEnum fromValue(String value) {
    for (StatusEnum b : StatusEnum.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
