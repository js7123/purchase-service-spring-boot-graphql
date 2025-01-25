package com.js.purchaseservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "customerName", nullable = false)
    private String customerName;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "phone1")
    private String phone1;

    @Column(name = "phone2")
    private String phone2;

    @Column(name = "phone3")
    private String phone3;

    public Customer() {
    }

    public Customer(Long id, String customerName, String street, String city, String state, String zip, String phone1
            , String phone2, String phone3) {
        this.id = id;
        this.customerName = customerName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
    }

    public Customer(String customerName, String street, String city, String state, String zip, String phone1,
                    String phone2, String phone3) {
        this.customerName = customerName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
    }

    @Override
    public String toString() {
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("Customer [id=");
        strBuffer.append(id);
        strBuffer.append(", customerName=");
        strBuffer.append(customerName);
        strBuffer.append(", street=");
        strBuffer.append(street);
        strBuffer.append(", city=");
        strBuffer.append(city);
        strBuffer.append(", state=");
        strBuffer.append(state);
        strBuffer.append(", zip=");
        strBuffer.append(zip);
        strBuffer.append(", phone1=");
        strBuffer.append(phone1);
        strBuffer.append(", phone2=");
        strBuffer.append(phone2);
        strBuffer.append(", phone3=");
        strBuffer.append(phone3);
        return strBuffer.toString();
    }
}
