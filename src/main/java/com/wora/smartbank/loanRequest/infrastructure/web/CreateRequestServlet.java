package com.wora.smartbank.loanRequest.infrastructure.web;

import com.wora.smartbank.loanRequest.application.dto.RequestRequest;
import com.wora.smartbank.loanRequest.application.dto.RequestResponse;
import com.wora.smartbank.loanRequest.application.service.RequestService;
import com.wora.smartbank.loanRequest.domain.valueObject.Title;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet("/requests/create/*")
public class CreateRequestServlet extends HttpServlet {
    private final static Logger logger = LoggerFactory.getLogger(CreateRequestServlet.class);
    private final static String[] STEPS = {"project-info", "contact-info", "summary"};

    @Inject
    private RequestService service;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        final String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            forwardToStep(STEPS[0], req, res);
        } else {
            String step = pathInfo.substring(1);
            if (Arrays.asList(STEPS).contains(step)) {
                forwardToStep(step, req, res);
            } else {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        final String currentStep = req.getParameter("currentStep");
        if (currentStep == null) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Current step not specified");
            return;
        }

        int currentStepIndex = Arrays.asList(STEPS).indexOf(currentStep);
        if (currentStepIndex == -1) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid step");
            return;
        }

        storeStepData(req, currentStep);

        if (currentStepIndex < STEPS.length - 1) {
            res.sendRedirect(req.getContextPath() + "/requests/create/" + STEPS[currentStepIndex + 1]);
        } else {
            submitRequest(req, res);
        }
    }

    private void storeStepData(HttpServletRequest req, String step) {
        Map<String, String> result = req.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));
        req.getSession().setAttribute(step, result);
    }

    private void submitRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
        final Map<String, String> sessionData = getDataFromSession(req);

        try {
            RequestRequest requestRequest = new RequestRequest(
                    sessionData.get("project"),
                    Double.parseDouble(sessionData.get("amount")),
                    Double.parseDouble(sessionData.get("duration")),
                    Double.parseDouble(sessionData.get("monthly")),
                    Title.valueOf(sessionData.get("title")),
                    sessionData.get("firstName"),
                    sessionData.get("lastName"),
                    sessionData.get("email"),
                    sessionData.get("phone"),
                    sessionData.get("profession"),
                    sessionData.get("cin"),
                    LocalDate.parse(sessionData.get("dateOfBirth")),
                    LocalDate.parse(sessionData.get("employmentStartDate")),
                    Double.parseDouble(sessionData.get("monthlyIncome")),
                    Boolean.parseBoolean(sessionData.get("hasExistingLoans"))
            );

            RequestResponse requestResponse = service.create(requestRequest);


            req.getSession().invalidate();
            res.sendRedirect(req.getContextPath() + "/requests/all");


        } catch (Exception e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to submit request: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getDataFromSession(HttpServletRequest req) {
        return Arrays.stream(STEPS)
                .flatMap(step -> {
                    Map<String, String> stepData = (Map<String, String>) req.getSession().getAttribute(step);
                    return stepData != null ? stepData.entrySet().stream() : Stream.empty();
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v2
                ));
    }

    private void forwardToStep(String step, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/requests/create/" + step + ".jsp").forward(req, res);
    }
}
