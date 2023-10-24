package com.provider.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.hibernate.validator.constraints.*;

/** ProviderGetAllReturnModel */
@JsonTypeName("providerGetAllReturnModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ProviderGetAllReturnModel {

  private Boolean ok;

  private ProviderGetAllReturnModelResult result;

  public ProviderGetAllReturnModel ok(Boolean ok) {
    this.ok = ok;
    return this;
  }

  /**
   * Get ok
   *
   * @return ok
   */
  @JsonProperty("ok")
  public Boolean isOk() {
    return ok;
  }

  public void setOk(Boolean ok) {
    this.ok = ok;
  }

  public ProviderGetAllReturnModel result(ProviderGetAllReturnModelResult result) {
    this.result = result;
    return this;
  }

  /**
   * Get result
   *
   * @return result
   */
  @Valid
  @JsonProperty("result")
  public ProviderGetAllReturnModelResult getResult() {
    return result;
  }

  public void setResult(ProviderGetAllReturnModelResult result) {
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
    ProviderGetAllReturnModel providerGetAllReturnModel = (ProviderGetAllReturnModel) o;
    return Objects.equals(this.ok, providerGetAllReturnModel.ok)
        && Objects.equals(this.result, providerGetAllReturnModel.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ok, result);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProviderGetAllReturnModel {\n");
    sb.append("    ok: ").append(toIndentedString(ok)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
