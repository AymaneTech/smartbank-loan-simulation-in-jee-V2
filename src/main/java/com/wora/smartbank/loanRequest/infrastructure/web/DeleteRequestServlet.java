package com.wora.smartbank.loanRequest.infrastructure.web;

import com.wora.smartbank.loanRequest.application.service.RequestService;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/requests/delete/*")
public class DeleteRequestServlet extends HttpServlet {

    @Inject
    private RequestService service;

    public void init() {
        System.out.println("init the delete servlet ");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        service.delete(getRequestId(req));

        res.sendRedirect(req.getContextPath() + "/requests/all");
    }

    public RequestId getRequestId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        assert pathInfo != null;
        String idStr = pathInfo.substring(1);
        return new RequestId(UUID.fromString(idStr));
    }
}
