Command for test app:

get all currencies
1)GET http://45.14.246.228:8080/CurrencyExchange/currencies

-------

get one currency
2)GET http://45.14.246.228:8080/CurrencyExchange/currency/USD

-------

save currency in db and return result
3)POST http://45.14.246.228:8080/CurrencyExchange/currencies
Content-Type: application/x-www-form-urlencoded

change code for check,because he can already exist
name = vitalik&
sign = ll&
code = llK

-----
get all ExchangeRates
4)GET http://45.14.246.228:8080/CurrencyExchange/exchangeRates

-----

get one ExchangeRates by codes name
5)GET http://45.14.246.228:8080/CurrencyExchange/exchangeRate/USDsss

-----

add Exchange rate
6)POST http://45.14.246.228:8080/CurrencyExchange/exchangeRates
Content-Type: application/x-www-form-urlencoded

change code for check
baseCurrencyCode = KMO&
targetCurrencyCode = sss&
rate = 0.99

-----

update method
7)POST http://45.14.246.228:8080/CurrencyExchange/exchangeRate/KMOsss
Content-Type: application/x-www-form-urlencoded

indicate data
rate = 0.67

-----

get exchange rate
8)GET http://45.14.246.228:8080/CurrencyExchange/exchange?from=KMO&to=sss&amount=15

