package com.provider.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.provider.persistence.entity.SubItem;

public interface SubItemRepository extends JpaRepository<SubItem, Long> {

}
