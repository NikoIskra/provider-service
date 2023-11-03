package com.provider.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import org.hibernate.validator.constraints.*;

/** Gets or Sets orderEnum */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public enum OrderEnum {
  ASC("ASC"),

  DESC("DESC");

  private String value;

  OrderEnum(String value) {
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
  public static OrderEnum fromValue(String value) {
    for (OrderEnum b : OrderEnum.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
