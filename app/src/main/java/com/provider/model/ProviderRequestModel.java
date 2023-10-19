package com.provider.model;

import java.util.*;
import java.util.Objects;

import org.hibernate.validator.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;

/**
 * ProviderRequestModel
 */

@JsonTypeName("providerRequestModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ProviderRequestModel {

  private String name;

  private String title;

  private String description;

  private String phoneNumber;

  /**
   * Default constructor
   * @deprecated Use {@link ProviderRequestModel#ProviderRequestModel(String, String, String)}
   */
  @Deprecated
  public ProviderRequestModel() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ProviderRequestModel(String name, String title, String phoneNumber) {
    this.name = name;
    this.title = title;
    this.phoneNumber = phoneNumber;
  }

  public ProviderRequestModel name(String name) {
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

  public ProviderRequestModel title(String title) {
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

  public ProviderRequestModel description(String description) {
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

  public ProviderRequestModel phoneNumber(String phoneNumber) {
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
    ProviderRequestModel providerRequestModel = (ProviderRequestModel) o;
    return Objects.equals(this.name, providerRequestModel.name) &&
        Objects.equals(this.title, providerRequestModel.title) &&
        Objects.equals(this.description, providerRequestModel.description) &&
        Objects.equals(this.phoneNumber, providerRequestModel.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, title, description, phoneNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProviderRequestModel {\n");
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
