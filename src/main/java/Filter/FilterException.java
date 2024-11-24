package Filter;

import Utils.ExceptionsHandler;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(value = {"/currencies","/currency/*","/exchangeRate/*","/exchangeRates","/exchange"})
public class FilterException implements Filter {    // here are added contentType and handler of all Exceptions in Project

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());


            filterChain.doFilter(servletRequest,servletResponse);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        }catch (Exception e){
                String error = ExceptionsHandler.getErrorMessage(e.getClass().getSimpleName(), (HttpServletResponse) servletResponse);
                servletResponse.getWriter().write(error);
        }


    }
}
