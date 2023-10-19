package com.provider.model;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.validator.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * ItemRequestModel
 */

@JsonTypeName("itemRequestModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ItemRequestModel {

  private String title;

  private String description;

  private Integer priceCents;

  @Valid
  private List<@Valid SubItemRequestModel> subItems;

  /**
   * Default constructor
   * @deprecated Use {@link ItemRequestModel#ItemRequestModel(String, Integer)}
   */
  @Deprecated
  public ItemRequestModel() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ItemRequestModel(String title, Integer priceCents) {
    this.title = title;
    this.priceCents = priceCents;
  }

  public ItemRequestModel title(String title) {
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

  public ItemRequestModel description(String description) {
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

  public ItemRequestModel priceCents(Integer priceCents) {
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

  public ItemRequestModel subItems(List<@Valid SubItemRequestModel> subItems) {
    this.subItems = subItems;
    return this;
  }

  public ItemRequestModel addSubItemsItem(SubItemRequestModel subItemsItem) {
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
  public List<@Valid SubItemRequestModel> getSubItems() {
    return subItems;
  }

  public void setSubItems(List<@Valid SubItemRequestModel> subItems) {
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
    ItemRequestModel itemRequestModel = (ItemRequestModel) o;
    return Objects.equals(this.title, itemRequestModel.title) &&
        Objects.equals(this.description, itemRequestModel.description) &&
        Objects.equals(this.priceCents, itemRequestModel.priceCents) &&
        Objects.equals(this.subItems, itemRequestModel.subItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, description, priceCents, subItems);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemRequestModel {\n");
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
