
![image](https://github.com/user-attachments/assets/c4b857bf-d5b1-4add-943c-54b7dffbcfca)


# Introduction.
This is the third project out of seven from the roadmap for a Java developer's work.This project is without FrontEnd, so you can test all commands in postman.All necessary commands for testing are at the end of this README.



## Project theme:
This project is about currency exchanger where you can add, read one, read all currencies in the database. Also you can create a currency exchange from the available currencies that you can add or view. The currency exchange you can also add, read one, read all and also update and get the exchange rate and how much money you will get if you convert from one to another currency if they exist in the database.


## Open website: 
http://45.14.246.228:8080/CurrencyExchange/currencies



## Technologies:
- Java Core - OOP,Colection
- Java Servlets
- JDBC
- Sqlite
- MVC
- Maven
- Tomcat
- Rest API,HTTP




# Command for test app:

### Get all currencies
1)GET http://45.14.246.228:8080/CurrencyExchange/currencies

-------

### Get one currency
2)GET http://45.14.246.228:8080/CurrencyExchange/currency/USD

-------

### Save currency in db and return result
3)POST http://45.14.246.228:8080/CurrencyExchange/currencies
Content-Type: application/x-www-form-urlencoded

Change code for check,because he can already exist:
+ name = Kazakhstani Tenge&
+ sign = ₸&
+ code = KZT

-----
### Get all ExchangeRates
4)GET http://45.14.246.228:8080/CurrencyExchange/exchangeRates

-----

### Get one ExchangeRates by codes name
5)GET http://45.14.246.228:8080/CurrencyExchange/exchangeRate/USDEUR

-----

### Add Exchange rate
6)POST http://45.14.246.228:8080/CurrencyExchange/exchangeRates
Content-Type: application/x-www-form-urlencoded

Change code for check:
+ baseCurrencyCode = USD&
+ targetCurrencyCode = EUR&
+ rate = 0.99

-----

### Update method
7)POST http://45.14.246.228:8080/CurrencyExchange/exchangeRate/USDEUR
Content-Type: application/x-www-form-urlencoded

Indicate data:
+ rate = 0.67

-----

### Get exchange rate
8)GET http://45.14.246.228:8080/CurrencyExchange/exchange?from=USD&to=EUR&amount=15
