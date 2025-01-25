package com.js.purchaseservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StockItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lineItem_id", nullable = false, updatable = false)
    private LineItem lineItem;

    @Column(name = "price", nullable = false)
    private Float price;


    @Column(name = "taxRate", nullable = false)
    private Float taxRate;

    public StockItem() {
    }

    public StockItem(Long id, LineItem lineItem, Float price, Float taxRate) {
        this.id = id;
        this.lineItem = lineItem;
        this.price = price;
        this.taxRate = taxRate;
    }

    public StockItem(LineItem lineItem, Float price, Float taxRate) {
        this.lineItem = lineItem;
        this.price = price;
        this.taxRate = taxRate;
    }

    @Override
    public String toString() {
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("StockItem [id=");
        strBuffer.append(id);
        strBuffer.append(", lineItem=");
        strBuffer.append(lineItem);
        strBuffer.append(", price=");
        strBuffer.append(price);
        strBuffer.append(", taxRate=");
        strBuffer.append(taxRate);
        return strBuffer.toString();
    }
}
