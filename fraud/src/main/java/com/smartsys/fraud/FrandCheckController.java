package com.smartsys.fraud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartsys.clients.fraud.FraudCheckResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/fraud-check")
public record FrandCheckController(
    FraudCheckService fraudCheckService
) {
    @GetMapping(path = "{customerId}")
    public FraudCheckResponse isFraudster(
            @PathVariable("customerId") Integer customerId) {
        boolean isFraudulentCustomer = fraudCheckService.
                isFraudulentCustomer(customerId);
        log.info("fraud check request for customer {}", customerId);

        return new FraudCheckResponse(isFraudulentCustomer);
    }

}
