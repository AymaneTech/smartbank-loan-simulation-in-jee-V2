package com.wora.smartbank.loanRequest.infrastructure.web;

import com.wora.smartbank.loanRequest.application.dto.response.RequestWithHistory;
import com.wora.smartbank.loanRequest.application.service.RequestService;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/requests/show/*")
public class ShowRequestServlet extends HttpServlet {

    @Inject
    private RequestService service;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestWithHistory request = service.findByIdWithHistory(getRequestId(req));
        req.setAttribute("statusHistory", request.historyItems());
        req.setAttribute("request", request);

        req.getRequestDispatcher("/pages/requests/request-details.jsp").forward(req, res);
    }

    public RequestId getRequestId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        assert pathInfo != null;
        String idStr = pathInfo.substring(1);
        return new RequestId(UUID.fromString(idStr));
    }
}
