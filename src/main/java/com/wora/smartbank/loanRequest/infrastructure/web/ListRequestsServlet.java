package com.wora.smartbank.loanRequest.infrastructure.web;

import com.wora.smartbank.loanRequest.application.dto.response.RequestResponse;
import com.wora.smartbank.loanRequest.application.dto.response.StatusResponse;
import com.wora.smartbank.loanRequest.application.service.RequestService;
import com.wora.smartbank.loanRequest.application.service.StatusService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/requests/all")
public class ListRequestsServlet extends HttpServlet {

    @Inject
    private RequestService service;

    @Inject
    private StatusService statusService;

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String statusName = req.getParameter("filterByStatus");
        final List<StatusResponse> statuses = statusService.findAll();
        final List<RequestResponse> requests = service.findAll();

        req.setAttribute("statuses", statuses);
        req.setAttribute("requests", requests);
        req.getRequestDispatcher("/pages/requests/request-list.jsp").forward(req, res);
    }
}

