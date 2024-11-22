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
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");
        try {
            CurrenciesService currenciesService = new CurrenciesService();
            List<CurrenciesDTO> currencies = currenciesService.getAllCurrencies();

            String json = new Gson().toJson(currencies);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(json);
        }catch (SomeThingWrongWithBDException e){
            String error = ExceptionsHandler.getErrorMessage(e.getClass().getSimpleName(),resp);
            resp.getWriter().write(error);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try{
            String code = req.getParameter("code");
            String name = req.getParameter("name");
            String sign = req.getParameter("sign");

            ValidationServlets.checkParametersForCorrect(code, name, sign);

            CurrenciesDTO currenciesDTO = new CurrenciesDTO(code, name, sign);
            CurrenciesService currenciesService = new CurrenciesService();
            CurrenciesDTO currenciesDTOResult =  currenciesService.add(currenciesDTO);

            String json = new Gson().toJson(currenciesDTOResult);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(json);
        }catch (Exception e){
            String error = ExceptionsHandler.getErrorMessage(e.getClass().getSimpleName(),resp);
            resp.getWriter().write(error);
        }

    }
}
