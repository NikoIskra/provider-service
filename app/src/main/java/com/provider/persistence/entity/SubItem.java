package com.provider.persistence.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "sub_item")
public class SubItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subItemSequenceGenerator")
    @SequenceGenerator(name = "subItemSequenceGenerator", sequenceName = "sub_item_id_seq", allocationSize = 1)    
    private Long id;

    @Column(nullable = false)
    private String title;


    private String description;

    @Column(name = "price_cents",nullable = false)
    private Integer priceCents;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", insertable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public SubItem() {
    }

    public SubItem(String title, Integer priceCents, String status) {
        this.title = title;
        this.priceCents = priceCents;
        this.status = status;
    }

    public SubItem(String title, Integer priceCents, String status, Item item) {
        this.title = title;
        this.priceCents = priceCents;
        this.status = status;
        this.item = item;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
