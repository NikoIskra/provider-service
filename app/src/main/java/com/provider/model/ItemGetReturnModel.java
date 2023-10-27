package com.provider.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.provider.model.ItemGetReturnModelResult;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ItemGetReturnModel
 */

@JsonTypeName("itemGetReturnModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ItemGetReturnModel {

  private Boolean ok;

  private ItemGetReturnModelResult result;

  public ItemGetReturnModel ok(Boolean ok) {
    this.ok = ok;
    return this;
  }

  /**
   * Get ok
   * @return ok
  */
  
  @JsonProperty("ok")
  public Boolean isOk() {
    return ok;
  }

  public void setOk(Boolean ok) {
    this.ok = ok;
  }

  public ItemGetReturnModel result(ItemGetReturnModelResult result) {
    this.result = result;
    return this;
  }

  /**
   * Get result
   * @return result
  */
  @Valid 
  @JsonProperty("result")
  public ItemGetReturnModelResult getResult() {
    return result;
  }

  public void setResult(ItemGetReturnModelResult result) {
    this.result = result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemGetReturnModel itemGetReturnModel = (ItemGetReturnModel) o;
    return Objects.equals(this.ok, itemGetReturnModel.ok) &&
        Objects.equals(this.result, itemGetReturnModel.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ok, result);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemGetReturnModel {\n");
    sb.append("    ok: ").append(toIndentedString(ok)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

