package com.provider.persistence.repository;

import com.provider.model.StatusEnum;
import com.provider.persistence.entity.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
  Boolean existsByIdAndProviderId(Long itemID, Long providerID);

  List<Item> findTop10ByProviderIdAndStatusIn(Long providerID, List<StatusEnum> statusList);
}
