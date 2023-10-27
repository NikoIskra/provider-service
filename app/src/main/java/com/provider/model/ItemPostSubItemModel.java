package com.provider.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.provider.model.StatusEnum;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ItemPostSubItemModel
 */

@JsonTypeName("itemPostSubItemModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ItemPostSubItemModel {

  private Long id;

  private Long itemId;

  private String title;

  private String description;

  private Integer priceCents;

  private StatusEnum status;

  public ItemPostSubItemModel id(Long id) {
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

  public ItemPostSubItemModel itemId(Long itemId) {
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

  public ItemPostSubItemModel title(String title) {
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

  public ItemPostSubItemModel description(String description) {
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

  public ItemPostSubItemModel priceCents(Integer priceCents) {
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

  public ItemPostSubItemModel status(StatusEnum status) {
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
    ItemPostSubItemModel itemPostSubItemModel = (ItemPostSubItemModel) o;
    return Objects.equals(this.id, itemPostSubItemModel.id) &&
        Objects.equals(this.itemId, itemPostSubItemModel.itemId) &&
        Objects.equals(this.title, itemPostSubItemModel.title) &&
        Objects.equals(this.description, itemPostSubItemModel.description) &&
        Objects.equals(this.priceCents, itemPostSubItemModel.priceCents) &&
        Objects.equals(this.status, itemPostSubItemModel.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, itemId, title, description, priceCents, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemPostSubItemModel {\n");
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

