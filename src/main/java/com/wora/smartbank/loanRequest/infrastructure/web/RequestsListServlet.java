package com.wora.smartbank.loanRequest.infrastructure.web;

import com.wora.smartbank.loanRequest.application.dto.RequestResponse;
import com.wora.smartbank.loanRequest.application.service.RequestService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/requests/all")
public class RequestsListServlet extends HttpServlet {

    @Inject
    private RequestService service;

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final List<RequestResponse> requests = service.findAll();
        req.setAttribute("requests", requests);
        req.getRequestDispatcher("/pages/requests/request-list.jsp").forward(req, res);
    }
}
