package Servlets;

import DTO.ExchangeRatesDTO;
import Service.ExchangeRatesService;
import Utils.ExceptionsHandler;
import Validation.ValidationServlets;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRatesService exchangeRatesService = new ExchangeRatesService();

        List<ExchangeRatesDTO> exchangeRates = exchangeRatesService.getAllExchangeRates();

        String json = new Gson().toJson(exchangeRates);
        resp.getWriter().write(json);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRatesService exchangeRatesService = new ExchangeRatesService();

        String baseCurrency = req.getParameter("baseCurrencyCode");
        String targetCurrency = req.getParameter("targetCurrencyCode");

        ValidationServlets.checkParametersExchangeForCorrect(baseCurrency,targetCurrency,req.getParameter("rate"));    //place for validation
        BigDecimal rate =  new BigDecimal(req.getParameter("rate"));

        ExchangeRatesDTO exchangeRate = exchangeRatesService.addExchangeRates(baseCurrency,targetCurrency, rate);

        String json = new Gson().toJson(exchangeRate);
        resp.getWriter().write(json);
    }
}
