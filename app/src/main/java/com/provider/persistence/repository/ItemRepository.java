package com.provider.persistence.repository;

import com.provider.model.StatusEnum;
import com.provider.persistence.entity.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {
  Boolean existsByIdAndProviderId(Long itemID, Long providerID);

  List<Item> findTop10ByProviderIdAndStatusIn(Long providerID, List<StatusEnum> statusList);

  @Query(
      "select i from Item as i join fetch i.provider as p join fetch i.subItems as s where i.provider.id=?1 and i.id=?2 and s.status <> 'cancelled' and i.status <> 'cancelled'")
  Item getItemWithParentAndListOfChildren(Long providerID, Long itemID);
}
