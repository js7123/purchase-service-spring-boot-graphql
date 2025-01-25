package com.js.purchaseservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "poNo", nullable = false)
    private Long poNo;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @Column(name = "orderDate")
    private OffsetDateTime orderDate;

    @Column(name = "shipDate")
    private OffsetDateTime shipDate;

    @Column(name = "toStreet")
    private String toStreet;

    @Column(name = "toCity")
    private String toCity;

    @Column(name = "toState")
    private String toState;

    @Column(name = "toZip")
    private String toZip;

    public PurchaseOrder() {
    }

    public PurchaseOrder(Long id, Long poNo, Customer customer, OffsetDateTime orderDate, OffsetDateTime shipDate,
                         String toStreet,
                         String toCity, String toState, String toZip) {
        this.id = id;
        this.poNo = poNo;
        this.customer = customer;
        this.orderDate = orderDate;
        this.shipDate = shipDate;
        this.toStreet = toStreet;
        this.toCity = toCity;
        this.toState = toState;
        this.toZip = toZip;
    }

    public PurchaseOrder(Long poNo, Customer customer, OffsetDateTime orderDate, OffsetDateTime shipDate,
                         String toStreet, String toCity,
                         String toState, String toZip) {
        this.poNo = poNo;
        this.customer = customer;
        this.orderDate = orderDate;
        this.shipDate = shipDate;
        this.toStreet = toStreet;
        this.toCity = toCity;
        this.toState = toState;
        this.toZip = toZip;
    }

    @Override
    public String toString() {
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("PurchaseOrder [id=");
        strBuffer.append(id);
        strBuffer.append(", poNo=");
        strBuffer.append(poNo);
        strBuffer.append(", customer=");
        strBuffer.append(customer);
        strBuffer.append(", orderDate=");
        strBuffer.append(orderDate);
        strBuffer.append(", shipDate=");
        strBuffer.append(shipDate);
        strBuffer.append(", toStreet=");
        strBuffer.append(toStreet);
        strBuffer.append(", toCity=");
        strBuffer.append(toCity);
        strBuffer.append(", toState=");
        strBuffer.append(toState);
        strBuffer.append(", toZip=");
        strBuffer.append(toZip);
        return strBuffer.toString();
    }
}
