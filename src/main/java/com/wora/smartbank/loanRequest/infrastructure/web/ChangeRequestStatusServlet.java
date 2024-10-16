package com.wora.smartbank.loanRequest.infrastructure.web;

import com.wora.smartbank.loanRequest.application.dto.response.RequestResponse;
import com.wora.smartbank.loanRequest.application.dto.request.RequestStatusDto;
import com.wora.smartbank.loanRequest.application.dto.response.StatusResponse;
import com.wora.smartbank.loanRequest.application.service.RequestService;
import com.wora.smartbank.loanRequest.application.service.RequestStatusService;
import com.wora.smartbank.loanRequest.application.service.StatusService;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import com.wora.smartbank.loanRequest.domain.valueObject.StatusId;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/requests/change-status/*")
public class ChangeRequestStatusServlet extends HttpServlet {
    @Inject
    private RequestService requestService;
    @Inject
    private StatusService statusService;
    @Inject
    private RequestStatusService requestStatusService;

    private RequestId requestId;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        edit(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        update(req, res);
    }

    public void update(HttpServletRequest req, HttpServletResponse res) throws IOException {
        log("here here here here ");
        final Map<String, String> collectedData = req.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));
        StatusId statusId = new StatusId(UUID.fromString(collectedData.get("statusId")));

        requestStatusService.create(new RequestStatusDto(collectedData.get("description"), statusId, requestId));
        res.sendRedirect(req.getContextPath() + "/requests/all");
    }

    private void edit(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        requestId = getRequestId(req);
        RequestResponse request = requestService.findById(requestId);
        List<StatusResponse> statuses = statusService.findAll();

        req.setAttribute("loanRequest", request);
        req.setAttribute("statuses", statuses);
        req.getRequestDispatcher("/pages/requests/request-status-update.jsp").forward(req, res);
    }

    public RequestId getRequestId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        assert pathInfo != null;
        String idStr = pathInfo.substring(1);
        return new RequestId(UUID.fromString(idStr));
    }
}
