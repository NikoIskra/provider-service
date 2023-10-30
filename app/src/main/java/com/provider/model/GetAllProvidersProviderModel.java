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

/** GetAllProvidersProviderModel */
@JsonTypeName("getAllProvidersProviderModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class GetAllProvidersProviderModel {

  private Long id;

  private String name;

  private String title;

  private String description;

  private StatusEnum status;

  @Valid private List<String> services;

  private String phoneNumber;

  public GetAllProvidersProviderModel id(Long id) {
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

  public GetAllProvidersProviderModel name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public GetAllProvidersProviderModel title(String title) {
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

  public GetAllProvidersProviderModel description(String description) {
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

  public GetAllProvidersProviderModel status(StatusEnum status) {
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

  public GetAllProvidersProviderModel services(List<String> services) {
    this.services = services;
    return this;
  }

  public GetAllProvidersProviderModel addServicesItem(String servicesItem) {
    if (this.services == null) {
      this.services = new ArrayList<>();
    }
    this.services.add(servicesItem);
    return this;
  }

  /**
   * Get services
   *
   * @return services
   */
  @JsonProperty("services")
  public List<String> getServices() {
    return services;
  }

  public void setServices(List<String> services) {
    this.services = services;
  }

  public GetAllProvidersProviderModel phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Get phoneNumber
   *
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetAllProvidersProviderModel getAllProvidersProviderModel = (GetAllProvidersProviderModel) o;
    return Objects.equals(this.id, getAllProvidersProviderModel.id)
        && Objects.equals(this.name, getAllProvidersProviderModel.name)
        && Objects.equals(this.title, getAllProvidersProviderModel.title)
        && Objects.equals(this.description, getAllProvidersProviderModel.description)
        && Objects.equals(this.status, getAllProvidersProviderModel.status)
        && Objects.equals(this.services, getAllProvidersProviderModel.services)
        && Objects.equals(this.phoneNumber, getAllProvidersProviderModel.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, title, description, status, services, phoneNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetAllProvidersProviderModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    services: ").append(toIndentedString(services)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
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
