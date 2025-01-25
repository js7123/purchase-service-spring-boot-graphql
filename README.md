# purchase-service-spring-boot-graphql
Purchase Service implemented using Spring Booth and GraphQL.



# Project Overview

This project uses GraphQL to implement the Entity-Relationship diagram for Purchase Order Application shown below, and to support the related service calls.
That sample entity diagram was selected because it includes handling of many-to-many relationships and closer represents a non-trivial schema example that could be extended for production.
GraphQL and Spring Boot were used to build a service that supported that schema and many of the service calls that would be expected in a production environment. 


![Entity-Relationship Diagram for Purchase Application](https://github.com/js7123/purchase-service-spring-boot-graphql/blob/main/images/entity-diagram-for-purchase-application.gif)


For more details about the entity model please refer to [Entity-Relationship model](https://docs.oracle.com/cd/A91202_01/901_doc/appdev.901/a88878/adobjxmp.htm#439209).





## Purchase Service Calls

The service calls supported by the Purchase service are listed below.
For full details please see the service [Purchase Controller](https://github.com/js7123/purchase-service-spring-boot-graphql/blob/main/src/main/java/com/js/purchaseservice/controller/PurchaseController.java).


### Query Calls
- getCustomerByPurchaseOrderId( Long purchaseOrderId )
- findAllCustomers()
- findAllPurchaseOrders()
- findAllLineItems()
- findAllStockItems()

### Count Calls:
- countCustomers()
- countPurchaseOrders()
- countLineItems()
- countStockItems()

### Create Calls:
- createCustomer(...)
- createPurchaseOrder(...)
- createLineItem(...)
- createStockItem(...)

### Update Calls:
- updateCustomer(...)
- updateLineItem(...)
- updatePurchaseOrder(...)
- updateStockItem(...)

### Delete Calls:
- deleteCustomer( Long id )
- deletePurchaseOrder( Long id)
- deleteLineItem( Long id)
- deleteStockItem(( Long id)





## GraphQL

As mention in the Project Overview, GraphQL was used to implement a specific Entity-Relationship diagram for Purchase Order Application and to support the related service calls.

GrpahQL was also used to support unit testing for the application, including:
- create a customer
- get a customer
- delete a customer





## Spring Boot

The service is implemented as a Spring Boot application with Spring Boot version 3.3.3.





## Database

H2 database version 2.2.224 was used.





## Java

The service was implemented using Java version 23.





## Setup Notes

Prior to run the application replace in file src/main/resources/application.properties the entries '<YOUR_DB_USERNAME>' and '<YOUR_DB_PASSWORD>' by the corresponding database credentials.
Currently that aplication.properties file reads:
```
datasource.username=<YOUR_DB_USERNAME>
spring.datasource.password=<YOUR_DB_PASSWORD>
```




## References

[Oracle's Entity-Relationship Diagram](https://docs.oracle.com/cd/A91202_01/901_doc/appdev.901/a88878/adobjxmp.htm#439209)






## Contact
Project Link: https://github.com/js7123/purchase-service-spring-boot-graphql