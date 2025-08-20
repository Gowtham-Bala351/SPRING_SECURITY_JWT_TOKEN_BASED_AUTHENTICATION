package com.saveJsonServlet.saveJsonServlet.Controller;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RequestCachingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(req);

 
        String body = wrappedRequest.getBodyAsString();
        try {
            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
            wrappedRequest.setAttribute("parsedJson", jsonBody);
        } catch (Exception e) {
            e.printStackTrace();
        }

        chain.doFilter(wrappedRequest, response);
    }
}
