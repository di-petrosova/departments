package com.application.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.application.controller.CoreConstants.DEPARTMENTS;

public class RequestMapper extends HttpServlet {

    static Map<String, Controller> requestMapper = new HashMap<>();

    static {
        requestMapper.put(DEPARTMENTS, new DepController());
        requestMapper.put(DEPARTMENTS + "/edit", new DepController());
        requestMapper.put(DEPARTMENTS+ "/create", new DepController());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = getPureRequestURI(req);
        Controller controller = requestMapper.get(requestURI);
        controller.processGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = getPureRequestURI(req);
        Controller controller = requestMapper.get(requestURI);
        controller.processPost(req, resp);
    }

    private String getPureRequestURI(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        if (contextPath!=null) {
            requestURI = requestURI.substring(contextPath.length());
        }
        return requestURI;
    }
}
