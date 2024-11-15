package Servlets;

import DTO.CurrenciesDTO;
import Service.CurrenciesService;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CurrenciesService currenciesService = new CurrenciesService();
            List<CurrenciesDTO> currencies = currenciesService.getAllCurrencies();

            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
            resp.setContentType("application/json");
            String json = new Gson().toJson(currencies);
            resp.setStatus(HttpServletResponse.SC_OK);

            resp.getWriter().write(json);
        }catch (Exception e){
            String error = "{ \"message\" : \"Exchanges was not find\" }";
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(error);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrenciesDTO currenciesDTO = new CurrenciesDTO(
                req.getParameter("sign"),
                req.getParameter("name"),
                req.getParameter("code")
        );
        CurrenciesService currenciesService = new CurrenciesService();
        currenciesService.add(currenciesDTO);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
