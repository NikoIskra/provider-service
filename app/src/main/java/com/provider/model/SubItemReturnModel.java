package com.provider.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SubItemReturnModel
 */

@JsonTypeName("subItemReturnModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class SubItemReturnModel {

  private Long id;

  private Long itemId;

  private String title;

  private String description;

  private Integer priceCents;

  private String status;

  public SubItemReturnModel id(Long id) {
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

  public SubItemReturnModel itemId(Long itemId) {
    this.itemId = itemId;
    return this;
  }

  /**
   * Get itemId
   * @return itemId
  */
  
  @JsonProperty("itemId")
  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public SubItemReturnModel title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  */
  @Size(min = 5, max = 128) 
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public SubItemReturnModel description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  @Size(max = 512) 
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public SubItemReturnModel priceCents(Integer priceCents) {
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

  public SubItemReturnModel status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
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
    SubItemReturnModel subItemReturnModel = (SubItemReturnModel) o;
    return Objects.equals(this.id, subItemReturnModel.id) &&
        Objects.equals(this.itemId, subItemReturnModel.itemId) &&
        Objects.equals(this.title, subItemReturnModel.title) &&
        Objects.equals(this.description, subItemReturnModel.description) &&
        Objects.equals(this.priceCents, subItemReturnModel.priceCents) &&
        Objects.equals(this.status, subItemReturnModel.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, itemId, title, description, priceCents, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubItemReturnModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
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

