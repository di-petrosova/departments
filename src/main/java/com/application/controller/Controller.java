package com.application.controller;

import com.application.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Controller {
    void processGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    void processPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException;
}
