package com.wora.smartbank.loanRequest.infrastructure.seeder;

import com.wora.smartbank.loanRequest.application.dto.request.RequestRequest;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.valueObject.*;

import java.time.LocalDate;

/*
    THIS SEEDER IS USED ONLY FOR UNIT TESTING
 */
public class RequestSeeder {
    private static int count = 0;

    public static Request getRequest() {
        count++;
        return new Request(
                new LoanDetails("project " + count, 15000.00, 36.2, 450.00),
                new CustomerDetails(
                        Title.MR, "firstName " + count, "lastName " + count,
                        "email " + count + "@example.com",
                        "123-456-7890", "Engineer",
                        new Cin("ABC123456789"), LocalDate.of(1990, 5, 20), LocalDate.of(2015, 1, 10),
                        5000.00, false
                )
        ).setId(new RequestId());
    }

    public static RequestRequest getRequestRequest() {
        return new RequestRequest("project " + count, 20.2, 233.3, 22.33,
                Title.MR, "Aymane", "El Maini", "aymane@gmail.com",
                "082893939", "Developer", "03044", LocalDate.now(), LocalDate.now(),
                29293.3, true);
    }
}
