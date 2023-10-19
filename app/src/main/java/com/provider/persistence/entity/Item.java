package com.provider.persistence.entity;

import com.provider.converter.StatusEnumConverter;
import com.provider.model.StatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "item")
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemSequenceGenerator")
  @SequenceGenerator(
      name = "itemSequenceGenerator",
      sequenceName = "item_id_seq",
      allocationSize = 1)
  private Long id;

  @Column(nullable = false)
  private String title;

  private String description;

  @Column(name = "price_cents", nullable = false)
  private Integer priceCents;

  @Column(nullable = false)
  @Convert(converter = StatusEnumConverter.class)
  private StatusEnum status;

  @Column(name = "created_at", insertable = false)
  private Timestamp createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Timestamp updatedAt;

  @ManyToOne
  @JoinColumn(name = "provider_id")
  private Provider provider;

  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
  private List<SubItem> subItems;

  public Item() {}

  public Item(String title, Integer priceCents, StatusEnum status) {
    this.title = title;
    this.priceCents = priceCents;
    this.status = status;
  }

  public Item(
      String title,
      Integer priceCents,
      StatusEnum status,
      Provider provider,
      List<SubItem> subItems) {
    this.title = title;
    this.priceCents = priceCents;
    this.status = status;
    this.provider = provider;
    this.subItems = subItems;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Integer getPriceCents() {
    return priceCents;
  }

  public void setPriceCents(Integer priceCents) {
    this.priceCents = priceCents;
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

  public Provider getProvider() {
    return provider;
  }

  public void setProvider(Provider provider) {
    this.provider = provider;
  }

  public List<SubItem> getSubItems() {
    return subItems;
  }

  public void setSubItems(List<SubItem> subItems) {
    this.subItems = subItems;
  }
}
