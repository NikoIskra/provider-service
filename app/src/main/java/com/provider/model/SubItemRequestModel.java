package com.provider.model;

import java.util.*;
import java.util.Objects;

import org.hibernate.validator.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;

/**
 * SubItemRequestModel
 */

@JsonTypeName("subItemRequestModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class SubItemRequestModel {

  private String title;

  private String description;

  private Integer priceCents;

  /**
   * Default constructor
   * @deprecated Use {@link SubItemRequestModel#SubItemRequestModel(String, Integer)}
   */
  @Deprecated
  public SubItemRequestModel() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SubItemRequestModel(String title, Integer priceCents) {
    this.title = title;
    this.priceCents = priceCents;
  }

  public SubItemRequestModel title(String title) {
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

  public SubItemRequestModel description(String description) {
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

  public SubItemRequestModel priceCents(Integer priceCents) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubItemRequestModel subItemRequestModel = (SubItemRequestModel) o;
    return Objects.equals(this.title, subItemRequestModel.title) &&
        Objects.equals(this.description, subItemRequestModel.description) &&
        Objects.equals(this.priceCents, subItemRequestModel.priceCents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, description, priceCents);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubItemRequestModel {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    priceCents: ").append(toIndentedString(priceCents)).append("\n");
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
