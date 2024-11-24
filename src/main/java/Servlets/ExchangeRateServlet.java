package Servlets;

import DTO.ExchangeRatesDTO;
import Service.ExchangeRatesService;
import Utils.ExceptionsHandler;
import Validation.ValidationServlets;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRatesService exchangeRatesService = new ExchangeRatesService();

        String codesExchange = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1);    //one from two variant how take info from url
        ValidationServlets.checkCodeLengthExchangeRates(codesExchange);    //place for validation

        ExchangeRatesDTO exchangeRate = exchangeRatesService.getExchangeRate(codesExchange);

        String jsonRes = new Gson().toJson(exchangeRate);
        resp.getWriter().write(jsonRes);



    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRatesService exchangeRatesService = new ExchangeRatesService();

        String currencies = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1);
        ValidationServlets.checkUpdateParameters(req.getParameter("rate"),currencies);    //place for validation

        BigDecimal rate =  new BigDecimal(req.getParameter("rate"));
        ExchangeRatesDTO exchangeRate = exchangeRatesService.updateExchangeRates(currencies, rate);

        String json = new Gson().toJson(exchangeRate);
        resp.getWriter().write(json);

    }

}
