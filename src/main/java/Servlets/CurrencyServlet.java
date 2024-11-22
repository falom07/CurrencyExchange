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
        resp.setContentType("application/json");
        try{
            CurrenciesService currenciesService = new CurrenciesService();
            Gson gson = new Gson();
            String result;

            int servletLength = req.getServletPath().length() + 1;
            String code = req.getRequestURI().substring(servletLength);
            Validation.ValidationServlets.checkCodeLengthCurrency(code);
            CurrenciesDTO currenciesDTO = currenciesService.getCurrenciesByCode(code);


            result = gson.toJson(currenciesDTO, CurrenciesDTO.class);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(result);
        }catch (Exception e){
            String error = ExceptionsHandler.getErrorMessage(e.getClass().getSimpleName(),resp);
            resp.getWriter().write(error);
        }


    }

}
