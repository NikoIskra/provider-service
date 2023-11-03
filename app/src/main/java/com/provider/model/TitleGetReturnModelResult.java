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

/** TitleGetReturnModelResult */
@JsonTypeName("titleGetReturnModelResult")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class TitleGetReturnModelResult {

  @Valid private List<@Valid TitleGetModel> data;

  private Integer page;

  private Integer pageSize;

  private Integer numberOfPages;

  private String query;

  private String order;

  private String orderBy;

  public TitleGetReturnModelResult data(List<@Valid TitleGetModel> data) {
    this.data = data;
    return this;
  }

  public TitleGetReturnModelResult addDataItem(TitleGetModel dataItem) {
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
  public List<@Valid TitleGetModel> getData() {
    return data;
  }

  public void setData(List<@Valid TitleGetModel> data) {
    this.data = data;
  }

  public TitleGetReturnModelResult page(Integer page) {
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

  public TitleGetReturnModelResult pageSize(Integer pageSize) {
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

  public TitleGetReturnModelResult numberOfPages(Integer numberOfPages) {
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

  public TitleGetReturnModelResult query(String query) {
    this.query = query;
    return this;
  }

  /**
   * Get query
   *
   * @return query
   */
  @JsonProperty("query")
  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public TitleGetReturnModelResult order(String order) {
    this.order = order;
    return this;
  }

  /**
   * Get order
   *
   * @return order
   */
  @JsonProperty("order")
  public String getOrder() {
    return order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public TitleGetReturnModelResult orderBy(String orderBy) {
    this.orderBy = orderBy;
    return this;
  }

  /**
   * Get orderBy
   *
   * @return orderBy
   */
  @JsonProperty("orderBy")
  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TitleGetReturnModelResult titleGetReturnModelResult = (TitleGetReturnModelResult) o;
    return Objects.equals(this.data, titleGetReturnModelResult.data)
        && Objects.equals(this.page, titleGetReturnModelResult.page)
        && Objects.equals(this.pageSize, titleGetReturnModelResult.pageSize)
        && Objects.equals(this.numberOfPages, titleGetReturnModelResult.numberOfPages)
        && Objects.equals(this.query, titleGetReturnModelResult.query)
        && Objects.equals(this.order, titleGetReturnModelResult.order)
        && Objects.equals(this.orderBy, titleGetReturnModelResult.orderBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, page, pageSize, numberOfPages, query, order, orderBy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TitleGetReturnModelResult {\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
    sb.append("    numberOfPages: ").append(toIndentedString(numberOfPages)).append("\n");
    sb.append("    query: ").append(toIndentedString(query)).append("\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
    sb.append("    orderBy: ").append(toIndentedString(orderBy)).append("\n");
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
