package com.js.purchaseservice.repository;

import com.js.purchaseservice.model.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockItemRepository extends JpaRepository<StockItem, Long> {
}
