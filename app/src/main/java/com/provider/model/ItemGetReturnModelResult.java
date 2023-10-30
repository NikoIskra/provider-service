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

/** ItemGetReturnModelResult */
@JsonTypeName("itemGetReturnModelResult")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ItemGetReturnModelResult {

  private Long id;

  private String title;

  private String description;

  private Integer priceCents;

  private StatusEnum status;

  private GetItemsProviderModel provider;

  @Valid private List<@Valid GetItemsSubItemModel> subItems;

  public ItemGetReturnModelResult id(Long id) {
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

  public ItemGetReturnModelResult title(String title) {
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

  public ItemGetReturnModelResult description(String description) {
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

  public ItemGetReturnModelResult priceCents(Integer priceCents) {
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

  public ItemGetReturnModelResult status(StatusEnum status) {
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

  public ItemGetReturnModelResult provider(GetItemsProviderModel provider) {
    this.provider = provider;
    return this;
  }

  /**
   * Get provider
   *
   * @return provider
   */
  @Valid
  @JsonProperty("provider")
  public GetItemsProviderModel getProvider() {
    return provider;
  }

  public void setProvider(GetItemsProviderModel provider) {
    this.provider = provider;
  }

  public ItemGetReturnModelResult subItems(List<@Valid GetItemsSubItemModel> subItems) {
    this.subItems = subItems;
    return this;
  }

  public ItemGetReturnModelResult addSubItemsItem(GetItemsSubItemModel subItemsItem) {
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
  public List<@Valid GetItemsSubItemModel> getSubItems() {
    return subItems;
  }

  public void setSubItems(List<@Valid GetItemsSubItemModel> subItems) {
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
    ItemGetReturnModelResult itemGetReturnModelResult = (ItemGetReturnModelResult) o;
    return Objects.equals(this.id, itemGetReturnModelResult.id)
        && Objects.equals(this.title, itemGetReturnModelResult.title)
        && Objects.equals(this.description, itemGetReturnModelResult.description)
        && Objects.equals(this.priceCents, itemGetReturnModelResult.priceCents)
        && Objects.equals(this.status, itemGetReturnModelResult.status)
        && Objects.equals(this.provider, itemGetReturnModelResult.provider)
        && Objects.equals(this.subItems, itemGetReturnModelResult.subItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, priceCents, status, provider, subItems);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemGetReturnModelResult {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    priceCents: ").append(toIndentedString(priceCents)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    provider: ").append(toIndentedString(provider)).append("\n");
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
