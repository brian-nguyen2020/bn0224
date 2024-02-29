
# Project Title

The tools rental application is implemented with Java 17 and Java Spring Boot. For the demostration, the database is not required.  The available tools, rental charges and holidays data is loaded to memory with Java ConcurrentHashMap collection from the application startup. 
## Documentation

DIY_Tools_Rental_Flow_Diagram.pdf
DIY_Tools_Rental_ER_Diagram.pdf
Tools_Rental_Swagger.yaml


## Running Tests

Run checkout test on Postman with these tool codes: CHNS,LADW,JAKD,JAKR

POST http://localhost:8080/rental/checkout
body:
{
 "toolCode": "CHNS",
  "rentalDays": 5,
  "discountPercent": 10,
  "checkoutDate": "04/02/24"
}

Successful checkout will print Rental Agreement on application console.

```

