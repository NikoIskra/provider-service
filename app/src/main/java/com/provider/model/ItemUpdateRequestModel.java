package com.provider.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.hibernate.validator.constraints.*;

/** ItemUpdateRequestModel */
@JsonTypeName("itemUpdateRequestModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ItemUpdateRequestModel {

  private String title;

  private String description;

  private Integer priceCents;

  private StatusEnum status;

  /**
   * Default constructor
   *
   * @deprecated Use {@link ItemUpdateRequestModel#ItemUpdateRequestModel(String, Integer,
   *     StatusEnum)}
   */
  @Deprecated
  public ItemUpdateRequestModel() {
    super();
  }

  /** Constructor with only required parameters */
  public ItemUpdateRequestModel(String title, Integer priceCents, StatusEnum status) {
    this.title = title;
    this.priceCents = priceCents;
    this.status = status;
  }

  public ItemUpdateRequestModel title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   *
   * @return title
   */
  @NotNull
  @Size(min = 5, max = 128)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ItemUpdateRequestModel description(String description) {
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

  public ItemUpdateRequestModel priceCents(Integer priceCents) {
    this.priceCents = priceCents;
    return this;
  }

  /**
   * Get priceCents
   *
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

  public ItemUpdateRequestModel status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
   * @return status
   */
  @NotNull
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
    ItemUpdateRequestModel itemUpdateRequestModel = (ItemUpdateRequestModel) o;
    return Objects.equals(this.title, itemUpdateRequestModel.title)
        && Objects.equals(this.description, itemUpdateRequestModel.description)
        && Objects.equals(this.priceCents, itemUpdateRequestModel.priceCents)
        && Objects.equals(this.status, itemUpdateRequestModel.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, description, priceCents, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemUpdateRequestModel {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    priceCents: ").append(toIndentedString(priceCents)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
