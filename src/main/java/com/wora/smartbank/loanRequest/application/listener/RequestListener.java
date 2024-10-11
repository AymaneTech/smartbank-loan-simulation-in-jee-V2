package com.wora.smartbank.loanRequest.application.listener;

import com.wora.smartbank.loanRequest.application.dto.StatusRequest;
import com.wora.smartbank.loanRequest.application.service.StatusService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.List;

@WebListener
public class RequestListener implements ServletContextListener {

    @Inject
    private StatusService service;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (service.count() == 0) {
            service.createAll(List.of(
                            new StatusRequest("PENDING"),
                            new StatusRequest("CANCELED"),
                            new StatusRequest("ACCEPTED")
                    )
            );
        }
    }
}
