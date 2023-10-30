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

/** ItemPostReturnModelResult */
@JsonTypeName("itemPostReturnModelResult")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ItemPostReturnModelResult {

  private Long id;

  private Long providerId;

  private String title;

  private String description;

  private Integer priceCents;

  private StatusEnum status;

  @Valid private List<@Valid ItemPostSubItemModel> subItems;

  public ItemPostReturnModelResult id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ItemPostReturnModelResult providerId(Long providerId) {
    this.providerId = providerId;
    return this;
  }

  /**
   * Get providerId
   *
   * @return providerId
   */
  @JsonProperty("providerId")
  public Long getProviderId() {
    return providerId;
  }

  public void setProviderId(Long providerId) {
    this.providerId = providerId;
  }

  public ItemPostReturnModelResult title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   *
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

  public ItemPostReturnModelResult description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   *
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

  public ItemPostReturnModelResult priceCents(Integer priceCents) {
    this.priceCents = priceCents;
    return this;
  }

  /**
   * Get priceCents
   *
   * @return priceCents
   */
  @JsonProperty("priceCents")
  public Integer getPriceCents() {
    return priceCents;
  }

  public void setPriceCents(Integer priceCents) {
    this.priceCents = priceCents;
  }

  public ItemPostReturnModelResult status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
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

  public ItemPostReturnModelResult subItems(List<@Valid ItemPostSubItemModel> subItems) {
    this.subItems = subItems;
    return this;
  }

  public ItemPostReturnModelResult addSubItemsItem(ItemPostSubItemModel subItemsItem) {
    if (this.subItems == null) {
      this.subItems = new ArrayList<>();
    }
    this.subItems.add(subItemsItem);
    return this;
  }

  /**
   * Get subItems
   *
   * @return subItems
   */
  @Valid
  @JsonProperty("subItems")
  public List<@Valid ItemPostSubItemModel> getSubItems() {
    return subItems;
  }

  public void setSubItems(List<@Valid ItemPostSubItemModel> subItems) {
    this.subItems = subItems;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemPostReturnModelResult itemPostReturnModelResult = (ItemPostReturnModelResult) o;
    return Objects.equals(this.id, itemPostReturnModelResult.id)
        && Objects.equals(this.providerId, itemPostReturnModelResult.providerId)
        && Objects.equals(this.title, itemPostReturnModelResult.title)
        && Objects.equals(this.description, itemPostReturnModelResult.description)
        && Objects.equals(this.priceCents, itemPostReturnModelResult.priceCents)
        && Objects.equals(this.status, itemPostReturnModelResult.status)
        && Objects.equals(this.subItems, itemPostReturnModelResult.subItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, providerId, title, description, priceCents, status, subItems);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemPostReturnModelResult {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    providerId: ").append(toIndentedString(providerId)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    priceCents: ").append(toIndentedString(priceCents)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    subItems: ").append(toIndentedString(subItems)).append("\n");
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
