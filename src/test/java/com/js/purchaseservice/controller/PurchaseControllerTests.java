package com.js.purchaseservice.controller;

import com.js.purchaseservice.config.GraphQLConfig;
import com.js.purchaseservice.config.ScalarConfig;
import com.js.purchaseservice.model.Customer;
import com.js.purchaseservice.repository.CustomerRepository;
import com.js.purchaseservice.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AutoConfigureGraphQlTester
@ComponentScan("com.js.purchaseservice")
@Import(value = {GraphQLConfig.class, ScalarConfig.class})
@GraphQlTest
public class PurchaseControllerTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Configuration
    static class Config {}

    @Autowired
    GraphQlTester graphQlTester;

    @MockBean
    PurchaseService purchaseService;

    @MockBean
    CustomerRepository customerRepository;

    String fakeCustomerName_1 = "Tim O'Hara";
    String fakeCustomerName_2 = "Cameron Howe";

    @Test
    void givenAppContextWhenInjectedItShouldNotBeNull() {
        assertNotNull(applicationContext);
    }

    //@Order(1)
    @Test
    void createCustomer() {
        Customer fakeCustomer = new Customer();
        fakeCustomer.setId(1L);
        fakeCustomer.setCustomerName(fakeCustomerName_1);

        when(purchaseService.createCustomer(anyString(), anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString())).thenReturn(fakeCustomer);

        Customer customer = this.graphQlTester.documentName("createCustomer")
                .execute()
                .errors()
                .verify()
                .path("createCustomer")
                .entity(Customer.class)
                .get();
        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isNotNull();
        assertThat(customer.getCustomerName()).isEqualTo(fakeCustomerName_1);
    }


    @Test
    void getCustomer() {
        Customer fakeCustomer_1 = new Customer();
        fakeCustomer_1.setId(1L);
        fakeCustomer_1.setCustomerName(fakeCustomerName_1);

        Customer fakeCustomer_2 = new Customer();
        fakeCustomer_2.setId(2L);
        fakeCustomer_2.setCustomerName(fakeCustomerName_2);

        List<Customer> fakeCustomers = new ArrayList<>();
        fakeCustomers.add(fakeCustomer_1);
        fakeCustomers.add(fakeCustomer_2);

        when(purchaseService.findAllCustomers()).thenReturn(fakeCustomers);

        List<Customer> customers = this.graphQlTester.documentName("findAllCustomers")
                .execute()
                .errors()
                .verify()
                .path("findAllCustomers")
                .entityList(Customer.class)
                .get();

        assertEquals(2, customers.size());
        assertTrue(customers.stream().anyMatch(o -> o.getCustomerName().equals(fakeCustomerName_1)));
        assertTrue(customers.stream().anyMatch(o -> o.getCustomerName().equals(fakeCustomerName_2)));
    }

    @Test
    void deleteCustomer() {
        Long fakeCustomerID = 502L;
        Customer fakeCustomer_1 = new Customer();
        fakeCustomer_1.setId(fakeCustomerID);
        fakeCustomer_1.setCustomerName(fakeCustomerName_1);

        when(customerRepository.findById(fakeCustomerID)).thenReturn(Optional.of(fakeCustomer_1));
        doNothing().when(customerRepository).deleteById(anyLong());
        when(purchaseService.deleteCustomer(fakeCustomerID)).thenReturn(true);
        boolean hasDeletedCustomer = this.graphQlTester.documentName("deleteCustomer")
                .execute()
                .errors()
                .verify()
                .path("deleteCustomer")
                .entity(Boolean.class)
                .get();
        assertThat(hasDeletedCustomer).isTrue();
    }
}
