package com.js.purchaseservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "lineItemNo", nullable = false)
    private Long lineItemNo;

    @ManyToOne
    @JoinColumn(name = "purchaseOrder_id", nullable = false, updatable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "stockItem_id", nullable = false, updatable = false)
    private StockItem stockItem;

    @Column(name = "quantity")
    private Long quantity;


    @Column(name = "discount")
    private Float discount;

    public LineItem() {
    }

    public LineItem(Long id, Long lineItemNo, PurchaseOrder purchaseOrder, StockItem stockItem, Long quantity,
                    Float discount) {
        this.id = id;
        this.lineItemNo = lineItemNo;
        this.purchaseOrder = purchaseOrder;
        this.stockItem = stockItem;
        this.quantity = quantity;
        this.discount = discount;
    }

    public LineItem(Long lineItemNo, PurchaseOrder purchaseOrder, StockItem stockItem, Long quantity, Float discount) {
        this.lineItemNo = lineItemNo;
        this.purchaseOrder = purchaseOrder;
        this.stockItem = stockItem;
        this.quantity = quantity;
        this.discount = discount;
    }

    @Override
    public String toString() {
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("LineItem [id=");
        strBuffer.append(id);
        strBuffer.append(", lineItemNo=");
        strBuffer.append(lineItemNo);
        strBuffer.append(", purchaseOrder=");
        strBuffer.append(purchaseOrder);
        strBuffer.append(", stockItem=");
        strBuffer.append(stockItem);
        strBuffer.append(", quantity=");
        strBuffer.append(quantity);
        strBuffer.append(", discount=");
        strBuffer.append(discount);
        return strBuffer.toString();
    }
}
