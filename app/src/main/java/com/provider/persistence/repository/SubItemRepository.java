package com.provider.persistence.repository;

import com.provider.persistence.entity.SubItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubItemRepository extends JpaRepository<SubItem, Long> {}
