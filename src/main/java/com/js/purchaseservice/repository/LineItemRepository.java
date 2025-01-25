package com.js.purchaseservice.repository;

import com.js.purchaseservice.model.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemRepository extends JpaRepository<LineItem, Long> {
}
