package com.provider.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.provider.persistence.entity.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    Boolean existsByName (String name);
    Boolean existsByOwnerId(UUID ownerID);
    Boolean existsByIdAndOwnerId(Long providerID, UUID ownerID);
}
