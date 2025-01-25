package com.js.purchaseservice.service;

import com.js.purchaseservice.exception.CustomerNotFoundException;
import com.js.purchaseservice.exception.LineItemNotFoundException;
import com.js.purchaseservice.exception.PurchaseOrderNotFoundException;
import com.js.purchaseservice.exception.StockItemNotFoundException;
import com.js.purchaseservice.model.Customer;
import com.js.purchaseservice.model.LineItem;
import com.js.purchaseservice.model.PurchaseOrder;
import com.js.purchaseservice.model.StockItem;
import com.js.purchaseservice.repository.CustomerRepository;
import com.js.purchaseservice.repository.LineItemRepository;
import com.js.purchaseservice.repository.PurchaseOrderRepository;
import com.js.purchaseservice.repository.StockItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class PurchaseService {

    private final CustomerRepository customerRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final LineItemRepository lineItemRepository;
    private final StockItemRepository stockItemRepository;

    public PurchaseService(CustomerRepository customerRepository, PurchaseOrderRepository purchaseOrderRepository,
                    LineItemRepository lineItemRepository, StockItemRepository stockItemRepository) {
        this.customerRepository = customerRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.lineItemRepository = lineItemRepository;
        this.stockItemRepository = stockItemRepository;
    }

    public PurchaseOrder getPurchaseOrder(Long purchaseOrderId) {
        Optional<PurchaseOrder> optPurchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);
        if (optPurchaseOrder.isPresent()) {
            return optPurchaseOrder.get();
        }
        throw new EntityNotFoundException("Purchase Order provided not found. Provide a valid Purchase Order Id.");
    }

    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(null);
    }

    public Customer getCustomerByPurchaseOrderId(Long purchaseOrderId) {
        Optional<PurchaseOrder> optPurchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);
        if (optPurchaseOrder.isPresent()) {
            PurchaseOrder retrievedPurchaseOrder = optPurchaseOrder.get();
            return customerRepository.findById(retrievedPurchaseOrder.getCustomer().getId())
                    .orElseThrow(null);
        }

        throw new EntityNotFoundException("The purchase order Id provided was not found. Provide an existent purchase" +
                " order Id.");
    }

    public LineItem getLineItem(Long lineItemId) {
        return lineItemRepository.findById(lineItemId).orElseThrow(null);
    }

    public StockItem getStockItem(Long stockItemId) {
        return stockItemRepository.findById(stockItemId).orElseThrow(null);
    }

    public Iterable<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Iterable<PurchaseOrder> findAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public Iterable<LineItem> findAllLineItems() {
        return lineItemRepository.findAll();
    }

    public Iterable<StockItem> findAllStockItems() {
        return stockItemRepository.findAll();
    }

    public long countCustomers() {
        return customerRepository.count();
    }

    public long countPurchaseOrders() {
        return purchaseOrderRepository.count();
    }

    public Long countLineItems() {
        return lineItemRepository.count();
    }

    public Long countStockItems() {
        return stockItemRepository.count();
    }

    public Customer createCustomer(String customerName, String street, String city, String state, String zip,
                                   String phone1, String phone2, String phone3) {
        var customer = new Customer(customerName, street, city, state, zip, phone1, phone2, phone3);
        var persistedCustomer = customerRepository.save(customer);
        return new Customer(persistedCustomer.getId(), persistedCustomer.getCustomerName(),
                persistedCustomer.getStreet(), persistedCustomer.getCity(), persistedCustomer.getState(),
                persistedCustomer.getZip(), persistedCustomer.getPhone1(), persistedCustomer.getPhone2(),
                persistedCustomer.getPhone3());
    }

    public PurchaseOrder createPurchaseOrder(Long poNo, Long customer, OffsetDateTime orderDate, OffsetDateTime shipDate,
                                             String toStreet,
                                             String toCity, String toState, String toZip) {
        Optional<Customer> optCustomer = customerRepository.findById(customer);
        if (optCustomer.isEmpty()) {
            throw new EntityNotFoundException("Customer Id provided not found. Provide an existent customer Id.");
        }

        Customer retrievedCustomer = optCustomer.get();

        var purchaseOrder = new PurchaseOrder(poNo, retrievedCustomer, orderDate, shipDate, toStreet, toCity, toState
                ,  toZip);
        var persistedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        return new PurchaseOrder(persistedPurchaseOrder.getId(), persistedPurchaseOrder.getPoNo(),
                persistedPurchaseOrder.getCustomer(), persistedPurchaseOrder.getOrderDate(),
                persistedPurchaseOrder.getShipDate(), persistedPurchaseOrder.getToStreet(),
                persistedPurchaseOrder.getToCity(), persistedPurchaseOrder.getToState(),
                persistedPurchaseOrder.getToZip());
    }

    public LineItem createLineItem(Long lineItemNo, Long purchaseOrder, Long stockItem, Long quantity, Float discount) {
        Optional<PurchaseOrder> optPurchaseOrder = purchaseOrderRepository.findById(purchaseOrder);
        if (optPurchaseOrder.isEmpty()) {
            throw new PurchaseOrderNotFoundException("The purchase order Id provided was not found. Provide an " +
                    "existent purchase order Id.", "purchaseOrder");
        }

        PurchaseOrder retrievedPurchaseOrder = optPurchaseOrder.get();

        Optional<StockItem> optStockItem = stockItemRepository.findById(stockItem);
        if (optStockItem.isEmpty()) {
            throw new StockItemNotFoundException("The stock item Id provided was not found. Provide an existent stock " +
                    "item Id.", "stockItem");
        }

        StockItem retrievedStockItem = optStockItem.get();

        var lineItem = new LineItem(lineItemNo, retrievedPurchaseOrder, retrievedStockItem, quantity, discount);
        var persistedLineItem = lineItemRepository.save(lineItem);
        return new LineItem(persistedLineItem.getId(), persistedLineItem.getLineItemNo(),
                persistedLineItem.getPurchaseOrder(), persistedLineItem.getStockItem(),
                persistedLineItem.getQuantity(), persistedLineItem.getDiscount());
    }

    public StockItem createStockItem(Long lineItem, Float price, Float taxRate) {
        Optional<LineItem> optLineItem = lineItemRepository.findById(lineItem);
        if (optLineItem.isEmpty()) {
            throw new LineItemNotFoundException("The line item Id provided was not found. Provide an " +
                    "existent line item Id.", "lineItem");
        }
        LineItem retrievedLineItem = optLineItem.get();

        var stockItem = new StockItem(retrievedLineItem, price, taxRate);
        var persistedStockItem = stockItemRepository.save(stockItem);
        return new StockItem(persistedStockItem.getId(), persistedStockItem.getLineItem(),
                persistedStockItem.getPrice(), persistedStockItem.getTaxRate());
    }

    public Customer updateCustomer(Long id, String customerName, String street, String city, String state, String zip
            , String phone1, String phone2, String phone3) {

        Optional<Customer> optCustomer = customerRepository.findById(id);
        boolean missingFieldFound = false;
        StringBuffer missingFields = new StringBuffer();

        if (optCustomer.isPresent()) {
            Customer customer = optCustomer.get();
            if (customerName != null && !customerName.isEmpty()) {
                customer.setCustomerName(customerName);
            } else {
                missingFieldFound = true;
                missingFields.append("customerName");
            }

            if (street != null && !street.isEmpty()) {
                customer.setStreet(street);
            } else {
                missingFieldFound = true;
                missingFields.append("street");
            }

            if (city != null && city.isEmpty()) {
                customer.setCity(city);
            } else {
                missingFieldFound = true;
                missingFields.append("city");
            }

            if (state != null && !state.isEmpty()) {
                customer.setState(state);
            } else {
                missingFieldFound = true;
                missingFields.append("state");
            }

            if (zip != null && !zip.isEmpty()) {
                customer.setZip(zip);
            } else {
                missingFieldFound = true;
                missingFields.append("zip");
            }

            if (phone1 != null && !phone1.isEmpty()) {
                customer.setPhone1(phone1);
            } else {
                missingFieldFound = true;
                missingFields.append("phone1");
            }

            if (phone2 != null && !phone2.isEmpty()) {
                customer.setPhone2(phone2);
            } else {
                missingFieldFound = true;
                missingFields.append("phone2");
            }

            if (phone3 != null && !phone3.isEmpty()) {
                customer.setPhone3(phone3);
            } else {
                missingFieldFound = true;
                missingFields.append("phone3");
            }

            if (missingFieldFound) {
                throw new EntityNotFoundException("The following information was invalid or missing. Please provide " +
                        "correct information for the following fields: " + missingFields);
            }

            customerRepository.save(customer);
            return customer;
        }

        throw new CustomerNotFoundException("The customer id provided was not found. Provide an existent " +
                "customer Id.", "id");
    }

    public LineItem updateLineItem(Long id, Long lineItemNo, Long purchaseOrder, Long stockItem, Long quantity,
                                   Float discount) {
        Optional<LineItem> optLineItem = lineItemRepository.findById(id);

        boolean missingFieldFound = false;
        StringBuffer missingFields = new StringBuffer();

        if (optLineItem.isPresent()) {
            LineItem lineItem = optLineItem.get();

            if (lineItemNo != null) {
                lineItem.setLineItemNo(lineItemNo);
            } else {
                missingFieldFound = true;
                missingFields.append("lineItemNo");
            }

            if (purchaseOrder != null) {
                Optional<PurchaseOrder> optPurchaseOrder = purchaseOrderRepository.findById(purchaseOrder);
                if (optPurchaseOrder.isPresent()) {
                    PurchaseOrder retrievedPurchaseOrder = optPurchaseOrder.get();
                    lineItem.setPurchaseOrder(retrievedPurchaseOrder);
                } else {
                    missingFieldFound = true;
                    missingFields.append("purchaseOrder");
                }
            } else {
                missingFieldFound = true;
                missingFields.append("purchaseOrder");
            }

            if (stockItem != null) {
                Optional<StockItem> optStockItem = stockItemRepository.findById(stockItem);
                if (optStockItem.isPresent()) {
                    StockItem retrievedStockItem = optStockItem.get();
                    lineItem.setStockItem(retrievedStockItem);
                } else {
                    missingFieldFound = true;
                    missingFields.append("stockItem");
                }
            } else {
                missingFieldFound = true;
                missingFields.append("stockItem");
            }

            if (quantity != null) {
                lineItem.setQuantity(quantity);
            } else {
                missingFieldFound = true;
                missingFields.append("quantity");
            }

            if (discount != null) {
                lineItem.setDiscount(discount);
            } else {
                missingFieldFound = true;
                missingFields.append("quantity");
            }

            if (missingFieldFound) {
                throw new EntityNotFoundException("The following information was invalid or missing. Please provide " +
                        "correct information for the following fields: " + missingFields);
            }

            lineItemRepository.save(lineItem);
            return lineItem;
        }
        throw new LineItemNotFoundException("The line item id provided was not found. Provide an existent line " +
                "item id.", "id");
    }

    public PurchaseOrder updatePurchaseOrder(Long id, Long poNo, Long customer, OffsetDateTime orderDate, OffsetDateTime shipDate,
                                             String toStreet, String toCity, String toState, String toZip) {

        Optional<PurchaseOrder> optPurchaseOrder = purchaseOrderRepository.findById(id);

        boolean missingFieldFound = false;
        StringBuffer missingFields = new StringBuffer();

        if (optPurchaseOrder.isPresent()) {
            PurchaseOrder purchaseOrder = optPurchaseOrder.get();

            if (poNo != null) {
                purchaseOrder.setPoNo(poNo);
            } else {
                missingFieldFound = true;
                missingFields.append("poNo");
            }

            if (customer != null) {
                Optional<Customer> optCustomer = customerRepository.findById(customer);
                if (optCustomer.isPresent()) {
                    Customer retrievedCustomer = optCustomer.get();
                    purchaseOrder.setCustomer(retrievedCustomer);
                } else {
                    missingFieldFound = true;
                    missingFields.append("customer");
                }
            } else {
                missingFieldFound = true;
                missingFields.append("customer");
            }

            if (orderDate != null && shipDate != null && orderDate.isBefore(shipDate)) {
                purchaseOrder.setOrderDate(orderDate);
            } else {
                missingFieldFound = true;
                missingFields.append("orderDate");
            }

            if (shipDate != null) {
                purchaseOrder.setShipDate(shipDate);
            } else {
                missingFieldFound = true;
                missingFields.append("shipDate");
            }

            if (toStreet != null && !toStreet.isEmpty()) {
                purchaseOrder.setToStreet(toStreet);
            } else {
                missingFieldFound = true;
                missingFields.append("toStreet");
            }

            if (toCity != null && !toCity.isEmpty()) {
                purchaseOrder.setToCity(toCity);
            } else {
                missingFieldFound = true;
                missingFields.append("toCity");
            }

            if (toState != null && !toState.isEmpty()) {
                purchaseOrder.setToState(toState);
            } else {
                missingFieldFound = true;
                missingFields.append("toState");
            }

            if (toZip != null && !toZip.isEmpty()) {
                purchaseOrder.setToZip(toZip);
            } else {
                missingFieldFound = true;
                missingFields.append("toZip");
            }

            if (missingFieldFound) {
                throw new EntityNotFoundException("The following information was invalid or missing. Please provide " +
                        "correct information for following fields: " + missingFields);
            }

            purchaseOrderRepository.save(purchaseOrder);
            return purchaseOrder;
        }

        throw new PurchaseOrderNotFoundException("The purchase order id provided was not found. Provide an " +
                "existent purchase order id.", "id");
    }

    public StockItem updateStockItem(Long id, Long lineItem, Float price, Float taxRate) {
        Optional<StockItem> optStockItem = stockItemRepository.findById(id);

        boolean missingFieldFound = false;
        StringBuffer missingFields = new StringBuffer();

        if (optStockItem.isPresent()) {
            StockItem retrievedStockItem = optStockItem.get();

            if (lineItem != null) {
                Optional<LineItem> optLineItem = lineItemRepository.findById(lineItem);

                if (optLineItem.isPresent()) {
                    LineItem retrievedLineItem = optLineItem.get();
                    retrievedStockItem.setLineItem(retrievedLineItem);
                } else {
                    missingFieldFound = true;
                    missingFields.append("lineItem");
                }

            } else {
                missingFieldFound = true;
                missingFields.append("lineItem");
            }

            if (price != null) {
                retrievedStockItem.setPrice(price);
            } else {
                missingFieldFound = true;
                missingFields.append("price");
            }

            if (taxRate != null) {
                retrievedStockItem.setTaxRate(taxRate);
            } else {
                missingFieldFound = true;
                missingFields.append("taxRate");
            }

            if (missingFieldFound) {
                throw new EntityNotFoundException("The following information was invalid or missing. Please provide " +
                        "correct information for following fields: " + missingFields);
            }

            stockItemRepository.save(retrievedStockItem);
            return retrievedStockItem;
        }

        throw new StockItemNotFoundException("The stock item Id provided was not found. Provide an existent " +
                "stock item Id.", "id");
    }

    public boolean deleteCustomer(Long id) {
        customerRepository.deleteById(id);
        return true;
    }

    public boolean deletePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
        return true;
    }

    public boolean deleteLineItem(Long id) {
        lineItemRepository.deleteById(id);
        return true;
    }

    public boolean deleteStockItem(Long id) {
        stockItemRepository.deleteById(id);
        return true;
    }
}