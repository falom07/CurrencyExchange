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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
            List<ExchangeRatesDTO> exchangeRates = exchangeRatesService.getAllExchangeRates();

            Gson gson = new Gson();
            String json = gson.toJson(exchangeRates);
            response.getWriter().write(json);
        }catch (Exception e){
            String errorJson = ExceptionsHandler.getErrorMessage(e.getClass().getSimpleName(),response);
            response.getWriter().write(errorJson);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try{
            String baseCurrency = req.getParameter("baseCurrencyCode");
            String targetCurrency = req.getParameter("targetCurrencyCode");
            ValidationServlets.checkParametersExchangeForCorrect(baseCurrency,targetCurrency,req.getParameter("rate"));

            BigDecimal rate =  BigDecimal.valueOf(Double.parseDouble(req.getParameter("rate")));

            ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
            ExchangeRatesDTO exchangeRate = exchangeRatesService.addExchangeRates(baseCurrency,targetCurrency, rate);

            Gson gson = new Gson();
            String json = gson.toJson(exchangeRate);
            resp.getWriter().write(json);
        }catch (Exception e){
            String errorJson = ExceptionsHandler.getErrorMessage(e.getClass().getSimpleName(),resp);
            resp.getWriter().write(errorJson);
        }


    }
}
