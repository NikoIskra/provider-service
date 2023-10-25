package com.provider.persistence.entity;

import com.provider.converter.StatusEnumConverter;
import com.provider.model.StatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "provider")
public class Provider {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "providerSequenceGenerator")
  @SequenceGenerator(
      name = "providerSequenceGenerator",
      sequenceName = "provider_id_seq",
      allocationSize = 1)
  private Long id;

  @Column(name = "owner_id", nullable = false)
  private UUID ownerId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String title;

  private String description;

  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  @Convert(converter = StatusEnumConverter.class)
  private StatusEnum status;

  @Column(name = "created_at", insertable = false)
  private Timestamp createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Timestamp updatedAt;

  @OneToMany(mappedBy = "provider", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @Where(clause = "status != 'cancelled'")
  private List<Item> items;

  public Provider() {}

  public Provider(
      UUID ownerId,
      String name,
      String title,
      String phoneNumber,
      StatusEnum status,
      List<Item> items) {
    this.ownerId = ownerId;
    this.name = name;
    this.title = title;
    this.phoneNumber = phoneNumber;
    this.status = status;
    this.items = items;
  }

  public Provider(UUID ownerId, String name, String title, String phoneNumber, StatusEnum status) {
    this.ownerId = ownerId;
    this.name = name;
    this.title = title;
    this.phoneNumber = phoneNumber;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UUID getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(UUID ownerId) {
    this.ownerId = ownerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }
}
