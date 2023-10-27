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
 * ProviderPostRequestModel
 */

@JsonTypeName("providerPostRequestModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ProviderPostRequestModel {

  private String name;

  private String title;

  private String description;

  private String phoneNumber;

  /**
   * Default constructor
   * @deprecated Use {@link ProviderPostRequestModel#ProviderPostRequestModel(String, String, String)}
   */
  @Deprecated
  public ProviderPostRequestModel() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ProviderPostRequestModel(String name, String title, String phoneNumber) {
    this.name = name;
    this.title = title;
    this.phoneNumber = phoneNumber;
  }

  public ProviderPostRequestModel name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @NotNull @Size(min = 5, max = 64) 
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProviderPostRequestModel title(String title) {
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

  public ProviderPostRequestModel description(String description) {
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

  public ProviderPostRequestModel phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Get phoneNumber
   * @return phoneNumber
  */
  @NotNull @Pattern(regexp = "^\\+?\\d{10,14}$") 
  @JsonProperty("phoneNumber")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProviderPostRequestModel providerPostRequestModel = (ProviderPostRequestModel) o;
    return Objects.equals(this.name, providerPostRequestModel.name) &&
        Objects.equals(this.title, providerPostRequestModel.title) &&
        Objects.equals(this.description, providerPostRequestModel.description) &&
        Objects.equals(this.phoneNumber, providerPostRequestModel.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, title, description, phoneNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProviderPostRequestModel {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
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

