# Pdf Generator

**Requirements**
Java 17
Maven
Spring Boot
Thymeleaf
Flying Saucer (for rendering PDFs from HTML)

**Application will be running on Port 8080**

URL:http://localhost:8080/api/pdf/generate

Method: POST

**Add header:** 
key:Content-Type

value:application/json


**Request Body: JSON with invoice data**
{
  "seller": "XYZ Pvt. Ltd.",
  "sellerGstin": "29AABBCCDD121ZD",
  "sellerAddress": "New Delhi, India",
  "buyer": "Vedant Computers",
  "buyerGstin": "29AABBCCDD131ZD",
  "buyerAddress": "New Delhi, India",
  "items": [
    {
      "name": "Product 1",
      "quantity": "12 Nos",
      "rate": 123.00,
      "amount": 1476.00
    }
  ]
}

 pdfs will save in pdfs folder 
