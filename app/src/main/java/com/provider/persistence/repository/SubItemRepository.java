package com.provider.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.provider.persistence.entity.SubItem;

public interface SubItemRepository extends JpaRepository<SubItem, Long> {
    
}
