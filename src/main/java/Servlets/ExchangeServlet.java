package Servlets;

import DTO.ExchangeCurrenciesRateDTO;
import Service.ExchangeRatesService;
import Validation.ValidationServlets;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRatesService service = new ExchangeRatesService();

        String from  = req.getParameter("from");
        String to  = req.getParameter("to");
        String amount = req.getParameter("amount");

        ValidationServlets.checkParametersForExchange(from,to,amount);  //place for validation

        ExchangeCurrenciesRateDTO exchangeRatesDTO = service.exchangeCurrencies(from,to,new BigDecimal(amount));

        String json = new Gson().toJson(exchangeRatesDTO);
        resp.getWriter().write(json);


    }
}
