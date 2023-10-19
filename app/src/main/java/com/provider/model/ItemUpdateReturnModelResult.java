package com.provider.model;

import java.util.*;
import java.util.Objects;

import org.hibernate.validator.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * ItemUpdateReturnModelResult
 */

@JsonTypeName("itemUpdateReturnModel_result")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ItemUpdateReturnModelResult {

  private Long id;

  private Long providerId;

  private String title;

  private String description;

  private Integer priceCents;

  private StatusEnum status;

  public ItemUpdateReturnModelResult id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */

  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ItemUpdateReturnModelResult providerId(Long providerId) {
    this.providerId = providerId;
    return this;
  }

  /**
   * Get providerId
   * @return providerId
  */

  @JsonProperty("providerId")
  public Long getProviderId() {
    return providerId;
  }

  public void setProviderId(Long providerId) {
    this.providerId = providerId;
  }

  public ItemUpdateReturnModelResult title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  */

  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ItemUpdateReturnModelResult description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */

  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ItemUpdateReturnModelResult priceCents(Integer priceCents) {
    this.priceCents = priceCents;
    return this;
  }

  /**
   * Get priceCents
   * @return priceCents
  */

  @JsonProperty("priceCents")
  public Integer getPriceCents() {
    return priceCents;
  }

  public void setPriceCents(Integer priceCents) {
    this.priceCents = priceCents;
  }

  public ItemUpdateReturnModelResult status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @Valid
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemUpdateReturnModelResult itemUpdateReturnModelResult = (ItemUpdateReturnModelResult) o;
    return Objects.equals(this.id, itemUpdateReturnModelResult.id) &&
        Objects.equals(this.providerId, itemUpdateReturnModelResult.providerId) &&
        Objects.equals(this.title, itemUpdateReturnModelResult.title) &&
        Objects.equals(this.description, itemUpdateReturnModelResult.description) &&
        Objects.equals(this.priceCents, itemUpdateReturnModelResult.priceCents) &&
        Objects.equals(this.status, itemUpdateReturnModelResult.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, providerId, title, description, priceCents, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemUpdateReturnModelResult {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    providerId: ").append(toIndentedString(providerId)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    priceCents: ").append(toIndentedString(priceCents)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
