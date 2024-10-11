package com.wora.smartbank.loanRequest.infrastructure.web;

import com.wora.smartbank.loanRequest.application.dto.RequestRequest;
import com.wora.smartbank.loanRequest.application.dto.RequestResponse;
import com.wora.smartbank.loanRequest.application.service.RequestService;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import com.wora.smartbank.loanRequest.domain.valueObject.Title;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/requests/update/*")
public class UpdateRequestServlet extends HttpServlet {

    @Inject
    private RequestService service;

    private RequestId requestId;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        requestId = getRequestId(req);
        edit(req, res, requestId);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        update(req, res);
    }

    private void update(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Map<String, String> collectedData = req.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));

        RequestRequest requestDto = new RequestRequest(
                collectedData.get("project"),
                Double.parseDouble(collectedData.get("amount")),
                Double.parseDouble(collectedData.get("duration")),
                Double.parseDouble(collectedData.get("monthly")),
                Title.valueOf(collectedData.get("title")),
                collectedData.get("firstName"),
                collectedData.get("lastName"),
                collectedData.get("email"),
                collectedData.get("phone"),
                collectedData.get("profession"),
                collectedData.get("cin"),
                LocalDate.parse(collectedData.get("dateOfBirth")),
                LocalDate.parse(collectedData.get("employmentStartDate")),
                Double.parseDouble(collectedData.get("monthlyIncome")),
                Boolean.parseBoolean(collectedData.get("hasExistingLoans"))
        );
        service.update(requestId, requestDto);
        res.sendRedirect(req.getContextPath() + "/requests/all");
    }

    private void edit(HttpServletRequest req, HttpServletResponse res, RequestId id) throws ServletException, IOException {
        RequestResponse request = service.findById(id);
        System.out.println(request);
        req.setAttribute("loanRequest", request);
        req.getRequestDispatcher("/pages/requests/request-update.jsp").forward(req, res);
    }

    public RequestId getRequestId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        assert pathInfo != null;
        String idStr = pathInfo.substring(1);
        return new RequestId(UUID.fromString(idStr));
    }
}
