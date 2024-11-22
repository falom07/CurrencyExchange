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
        resp.setContentType("application/json");
        try {
            String codesExchange = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1);
            ValidationServlets.checkCodeLengthExchangeRates(codesExchange);

            ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
            ExchangeRatesDTO exchangeRate = exchangeRatesService.getExchangeRate(codesExchange);

            String jsonRes = new Gson().toJson(exchangeRate);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(jsonRes);
        }catch (Exception e){
            String error = ExceptionsHandler.getErrorMessage(e.getClass().getSimpleName(),resp);
            resp.getWriter().write(error);
        }


    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try{
            String currencies = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1);
            ValidationServlets.checkUpdateParameters(req.getParameter("rate"),currencies);


            BigDecimal rate =  BigDecimal.valueOf(Double.parseDouble(req.getParameter("rate")));

            ExchangeRatesService exchangeRatesService = new ExchangeRatesService();


            ExchangeRatesDTO exchangeRate = exchangeRatesService.updateExchangeRates(currencies, rate);

            Gson gson = new Gson();
            String json = gson.toJson(exchangeRate);
            resp.getWriter().write(json);
        }catch (Exception e){
            String error = ExceptionsHandler.getErrorMessage(e.getClass().getSimpleName(),resp);
            resp.getWriter().write(error);
        }
    }

}
