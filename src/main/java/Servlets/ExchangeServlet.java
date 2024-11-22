package Servlets;

import DTO.ExchangeCurrenciesRateDTO;
import DTO.ExchangeRatesDTO;
import Entity.CurrencyEntity;
import Service.ExchangeRatesService;
import Utils.ExceptionsHandler;
import Validation.ValidationServlets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try{
            String from  = req.getParameter("from");
            String to  = req.getParameter("to");
            String amount = req.getParameter("amount");

            ValidationServlets.checkParametersForExchange(from,to,amount);
            ExchangeCurrenciesRateDTO exchangeRatesDTO = new ExchangeCurrenciesRateDTO(
                    new CurrencyEntity(from),
                    new CurrencyEntity(to),
                    BigDecimal.valueOf(Double.parseDouble(amount))
            );

            ExchangeRatesService service = new ExchangeRatesService();
            exchangeRatesDTO = service.exchangeCurrencies(exchangeRatesDTO);

            String json = new Gson().toJson(exchangeRatesDTO);
            resp.getWriter().write(json);
        }catch(Exception e){
            String error = ExceptionsHandler.getErrorMessage(e.getClass().getSimpleName(),resp);
            resp.getWriter().write(error);
        }

    }
}
