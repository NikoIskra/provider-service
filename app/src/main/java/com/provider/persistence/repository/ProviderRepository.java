package com.provider.persistence.repository;

import com.provider.persistence.entity.Provider;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
  Boolean existsByName(String name);

  Boolean existsByOwnerId(UUID ownerID);

  Boolean existsByIdAndOwnerId(Long providerID, UUID ownerID);
}
