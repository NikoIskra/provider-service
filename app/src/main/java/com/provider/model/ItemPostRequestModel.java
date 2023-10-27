package com.provider.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.provider.model.SubItemPostRequestModel;
import java.util.ArrayList;
import java.util.List;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ItemPostRequestModel
 */

@JsonTypeName("itemPostRequestModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ItemPostRequestModel {

  private String title;

  private String description;

  private Integer priceCents;

  @Valid
  private List<@Valid SubItemPostRequestModel> subItems;

  /**
   * Default constructor
   * @deprecated Use {@link ItemPostRequestModel#ItemPostRequestModel(String, Integer)}
   */
  @Deprecated
  public ItemPostRequestModel() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ItemPostRequestModel(String title, Integer priceCents) {
    this.title = title;
    this.priceCents = priceCents;
  }

  public ItemPostRequestModel title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  */
  @NotNull @Size(min = 5, max = 128) 
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ItemPostRequestModel description(String description) {
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

  public ItemPostRequestModel priceCents(Integer priceCents) {
    this.priceCents = priceCents;
    return this;
  }

  /**
   * Get priceCents
   * @return priceCents
  */
  @NotNull 
  @JsonProperty("priceCents")
  public Integer getPriceCents() {
    return priceCents;
  }

  public void setPriceCents(Integer priceCents) {
    this.priceCents = priceCents;
  }

  public ItemPostRequestModel subItems(List<@Valid SubItemPostRequestModel> subItems) {
    this.subItems = subItems;
    return this;
  }

  public ItemPostRequestModel addSubItemsItem(SubItemPostRequestModel subItemsItem) {
    if (this.subItems == null) {
      this.subItems = new ArrayList<>();
    }
    this.subItems.add(subItemsItem);
    return this;
  }

  /**
   * Get subItems
   * @return subItems
  */
  @Valid 
  @JsonProperty("subItems")
  public List<@Valid SubItemPostRequestModel> getSubItems() {
    return subItems;
  }

  public void setSubItems(List<@Valid SubItemPostRequestModel> subItems) {
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
    ItemPostRequestModel itemPostRequestModel = (ItemPostRequestModel) o;
    return Objects.equals(this.title, itemPostRequestModel.title) &&
        Objects.equals(this.description, itemPostRequestModel.description) &&
        Objects.equals(this.priceCents, itemPostRequestModel.priceCents) &&
        Objects.equals(this.subItems, itemPostRequestModel.subItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, description, priceCents, subItems);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemPostRequestModel {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    priceCents: ").append(toIndentedString(priceCents)).append("\n");
    sb.append("    subItems: ").append(toIndentedString(subItems)).append("\n");
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

