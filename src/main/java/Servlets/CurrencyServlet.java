package Servlets;

import DTO.CurrenciesDTO;
import Service.CurrenciesService;
import Utils.ExceptionsHandler;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrenciesService currenciesService = new CurrenciesService();

        String code = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1);

        Validation.ValidationServlets.checkCodeLengthCurrency(code);    //place for validation
        CurrenciesDTO currenciesDTO = currenciesService.getCurrenciesByCode(code);

        String result = new Gson().toJson(currenciesDTO, CurrenciesDTO.class);
        resp.getWriter().write(result);



    }

}
