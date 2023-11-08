# WEX Challenge API

# About the project

Rest API for managing Purchase Transactions

# Endpoints
## Create Purchase Transaction
     (POST) http://localhost:8080/api/v1/purchase-transaction
      
        {
            "description": "Transaction description",
            "purchaseAmount": 1055.84
        }

        RESPONSE:

        {
            "id": 1,
            "description": "Transaction description",
            "transactionDate": "2023-11-08T15:53:27.437011",
            "purchaseAmount": 1055.84,          
        }
## Retrieve Purchase Transaction
    (GET) http://localhost:8080/api/v1/purchase-transaction/{id}/{currency}


# Technologies used
## Back end
- Java
- Spring Boot
- JPA/Hibernate
- Maven

# Author

Alan Naicson

https://www.linkedin.com/in/naicson88/
