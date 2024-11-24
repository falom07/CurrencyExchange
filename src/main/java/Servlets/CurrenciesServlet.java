package Servlets;

import DTO.CurrenciesDTO;
import Exceptions.SomeThingWrongWithBDException;
import Service.CurrenciesService;
import Utils.ExceptionsHandler;
import Validation.ValidationServlets;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CurrenciesService currenciesService = new CurrenciesService();

        List<CurrenciesDTO> currencies = currenciesService.getAllCurrencies();

        String json = new Gson().toJson(currencies);
        resp.getWriter().write(json);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrenciesService currenciesService = new CurrenciesService();

        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");

        ValidationServlets.checkParametersForCorrect(code, name, sign);   //place for validation

        CurrenciesDTO currenciesDTO = new CurrenciesDTO(code.toUpperCase(), name, sign);
        CurrenciesDTO currenciesDTOResult =  currenciesService.add(currenciesDTO);

        String json = new Gson().toJson(currenciesDTOResult);
        resp.getWriter().write(json);


    }
}
