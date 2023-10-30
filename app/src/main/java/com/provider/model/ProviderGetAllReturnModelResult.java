package com.provider.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.validator.constraints.*;

/** ProviderGetAllReturnModelResult */
@JsonTypeName("providerGetAllReturnModelResult")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ProviderGetAllReturnModelResult {

  @Valid private List<@Valid GetAllProvidersProviderModel> data;

  private Integer page;

  private Integer pageSize;

  private Integer numberOfPages;

  public ProviderGetAllReturnModelResult data(List<@Valid GetAllProvidersProviderModel> data) {
    this.data = data;
    return this;
  }

  public ProviderGetAllReturnModelResult addDataItem(GetAllProvidersProviderModel dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<>();
    }
    this.data.add(dataItem);
    return this;
  }

  /**
   * Get data
   *
   * @return data
   */
  @Valid
  @JsonProperty("data")
  public List<@Valid GetAllProvidersProviderModel> getData() {
    return data;
  }

  public void setData(List<@Valid GetAllProvidersProviderModel> data) {
    this.data = data;
  }

  public ProviderGetAllReturnModelResult page(Integer page) {
    this.page = page;
    return this;
  }

  /**
   * Get page
   *
   * @return page
   */
  @JsonProperty("page")
  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public ProviderGetAllReturnModelResult pageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  /**
   * Get pageSize
   *
   * @return pageSize
   */
  @JsonProperty("pageSize")
  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public ProviderGetAllReturnModelResult numberOfPages(Integer numberOfPages) {
    this.numberOfPages = numberOfPages;
    return this;
  }

  /**
   * Get numberOfPages
   *
   * @return numberOfPages
   */
  @JsonProperty("numberOfPages")
  public Integer getNumberOfPages() {
    return numberOfPages;
  }

  public void setNumberOfPages(Integer numberOfPages) {
    this.numberOfPages = numberOfPages;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProviderGetAllReturnModelResult providerGetAllReturnModelResult =
        (ProviderGetAllReturnModelResult) o;
    return Objects.equals(this.data, providerGetAllReturnModelResult.data)
        && Objects.equals(this.page, providerGetAllReturnModelResult.page)
        && Objects.equals(this.pageSize, providerGetAllReturnModelResult.pageSize)
        && Objects.equals(this.numberOfPages, providerGetAllReturnModelResult.numberOfPages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, page, pageSize, numberOfPages);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProviderGetAllReturnModelResult {\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
    sb.append("    numberOfPages: ").append(toIndentedString(numberOfPages)).append("\n");
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
