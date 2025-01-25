package com.js.purchaseservice.controller;

import com.js.purchaseservice.model.Customer;
import com.js.purchaseservice.model.LineItem;
import com.js.purchaseservice.model.PurchaseOrder;
import com.js.purchaseservice.model.StockItem;
import com.js.purchaseservice.service.PurchaseService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;

@Controller
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @QueryMapping
    public Customer getCustomerByPurchaseOrderId(@Argument Long purchaseOrderId) {
        return purchaseService.getCustomerByPurchaseOrderId(purchaseOrderId);
    }

    @QueryMapping
    public Iterable<Customer> findAllCustomers() {
        return purchaseService.findAllCustomers();
    }

    @QueryMapping
    public Iterable<PurchaseOrder> findAllPurchaseOrders() {
        return purchaseService.findAllPurchaseOrders();
    }

    @QueryMapping
    public Iterable<LineItem> findAllLineItems() {
        return purchaseService.findAllLineItems();
    }

    @QueryMapping
    public Iterable<StockItem> findAllStockItems() {
        return purchaseService.findAllStockItems();
    }

    @QueryMapping
    public long countCustomers() {
        return purchaseService.countCustomers();
    }

    @QueryMapping
    public long countPurchaseOrders() {
        return purchaseService.countPurchaseOrders();
    }

    @QueryMapping
    public long countLineItems() {
        return purchaseService.countLineItems();
    }

    @QueryMapping
    public long countStockItems() {
        return purchaseService.countStockItems();
    }

    @MutationMapping
    public Customer createCustomer(@Argument String customerName, @Argument String street, @Argument String city,
                                   @Argument String state, @Argument String zip, @Argument String phone1,
                                   @Argument String phone2, @Argument String phone3) {
        return purchaseService.createCustomer(customerName, street, city, state, zip, phone1, phone2, phone3);
    }

    @MutationMapping
    public PurchaseOrder createPurchaseOrder(@Argument Long poNo, @Argument Long customer, @Argument OffsetDateTime orderDate,
                                             @Argument OffsetDateTime shipDate, @Argument String toStreet,
                                             @Argument String toCity, @Argument String toState,
                                             @Argument String toZip) {
        return purchaseService.createPurchaseOrder(poNo, customer, orderDate, shipDate, toStreet, toCity, toState,
                toZip);
    }

    @MutationMapping
    public LineItem createLineItem(@Argument Long lineItemNo, @Argument Long purchaseOrder, @Argument Long stockItem,
                                   @Argument Long quantity, @Argument Float discount) {
        return purchaseService.createLineItem(lineItemNo, purchaseOrder, stockItem, quantity, discount);
    }

    @MutationMapping
    public StockItem createStockItem(@Argument Long lineItem, @Argument Float price, @Argument Float taxRate) {
        return purchaseService.createStockItem(lineItem, price, taxRate);
    }

    @MutationMapping
    public Customer updateCustomer(@Argument Long id, @Argument String customerName, @Argument String street,
                                   @Argument String city, @Argument String state, @Argument String zip,
                                   @Argument String phone1, @Argument String phone2, @Argument String phone3) {
        return purchaseService.updateCustomer(id, customerName, street, city, state, zip, phone1, phone2, phone3);
    }

    @MutationMapping
    public LineItem updateLineItem(@Argument Long id, @Argument Long lineItemNo, @Argument Long purchaseOrder,
                                   @Argument Long stockItem, @Argument Long quantity, @Argument Float discount) {
        return purchaseService.updateLineItem(id, lineItemNo, purchaseOrder, stockItem, quantity, discount);
    }

    @MutationMapping
    public PurchaseOrder updatePurchaseOrder(@Argument Long id, @Argument Long poNo, @Argument Long customer,
                                             @Argument OffsetDateTime orderDate, @Argument OffsetDateTime shipDate,
                                             @Argument String toStreet, @Argument String toCity,
                                             @Argument String toState, @Argument String toZip) {
        return purchaseService.updatePurchaseOrder(id, poNo, customer, orderDate, shipDate, toStreet, toCity, toState
                , toZip);
    }

    @MutationMapping
    public StockItem updateStockItem(@Argument Long id, @Argument Long lineItem, @Argument Float price,
                                     @Argument Float taxRate) {
        return purchaseService.updateStockItem(id, lineItem, price, taxRate);
    }

    @MutationMapping
    public boolean deleteCustomer(@Argument Long id) {
        return purchaseService.deleteCustomer(id);
    }

    @MutationMapping
    public boolean deletePurchaseOrder(@Argument Long id) {
        return purchaseService.deletePurchaseOrder(id);
    }

    @MutationMapping
    public boolean deleteLineItem(@Argument Long id) {
        return purchaseService.deleteLineItem(id);
    }

    @MutationMapping
    public boolean deleteStockItem(@Argument Long id) {
        return purchaseService.deleteStockItem(id);
    }
}
