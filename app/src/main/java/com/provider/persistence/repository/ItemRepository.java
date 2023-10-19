package com.provider.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.provider.persistence.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Boolean existsByIdAndProviderId (Long itemID, Long providerID);
}
