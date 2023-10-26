package com.provider.persistence.repository;

import com.provider.model.StatusEnum;
import com.provider.persistence.entity.Provider;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
  Boolean existsByName(String name);

  Boolean existsByOwnerId(UUID ownerID);

  Boolean existsByIdAndOwnerId(Long providerID, UUID ownerID);

  Page<Provider> findAllByStatusIn(Pageable pageable, List<StatusEnum> statusList);

  @Query(
      "select p from Provider as p join fetch p.items as i where i.status <> 'cancelled' and p.status <> 'cancelled'")
  Page<Provider> findAllWithChildren(Pageable pageable);
}
