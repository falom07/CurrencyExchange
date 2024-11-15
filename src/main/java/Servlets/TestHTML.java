package Servlets;

import Service.CurrenciesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/testPage")
public class TestHTML extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrenciesService currenciesService = new CurrenciesService();
        resp.setContentType("text/html");

        req.getRequestDispatcher("index.html").forward(req, resp);
    }
}
