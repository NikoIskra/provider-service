package com.provider.persistence.repository;

import com.provider.persistence.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
  Boolean existsByIdAndProviderId(Long itemID, Long providerID);
}
