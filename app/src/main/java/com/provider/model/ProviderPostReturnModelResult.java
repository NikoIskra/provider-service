package com.provider.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.provider.model.StatusEnum;
import java.util.UUID;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ProviderPostReturnModelResult
 */

@JsonTypeName("providerPostReturnModelResult")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ProviderPostReturnModelResult {

  private Long id;

  private UUID ownerId;

  private String name;

  private String title;

  private String description;

  private String phoneNumber;

  private StatusEnum status;

  public ProviderPostReturnModelResult id(Long id) {
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

  public ProviderPostReturnModelResult ownerId(UUID ownerId) {
    this.ownerId = ownerId;
    return this;
  }

  /**
   * Get ownerId
   * @return ownerId
  */
  @Valid 
  @JsonProperty("ownerId")
  public UUID getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(UUID ownerId) {
    this.ownerId = ownerId;
  }

  public ProviderPostReturnModelResult name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProviderPostReturnModelResult title(String title) {
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

  public ProviderPostReturnModelResult description(String description) {
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

  public ProviderPostReturnModelResult phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Get phoneNumber
   * @return phoneNumber
  */
  @Pattern(regexp = "^\\+?\\d{10,14}$") 
  @JsonProperty("phoneNumber")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public ProviderPostReturnModelResult status(StatusEnum status) {
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
    ProviderPostReturnModelResult providerPostReturnModelResult = (ProviderPostReturnModelResult) o;
    return Objects.equals(this.id, providerPostReturnModelResult.id) &&
        Objects.equals(this.ownerId, providerPostReturnModelResult.ownerId) &&
        Objects.equals(this.name, providerPostReturnModelResult.name) &&
        Objects.equals(this.title, providerPostReturnModelResult.title) &&
        Objects.equals(this.description, providerPostReturnModelResult.description) &&
        Objects.equals(this.phoneNumber, providerPostReturnModelResult.phoneNumber) &&
        Objects.equals(this.status, providerPostReturnModelResult.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ownerId, name, title, description, phoneNumber, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProviderPostReturnModelResult {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    ownerId: ").append(toIndentedString(ownerId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
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

