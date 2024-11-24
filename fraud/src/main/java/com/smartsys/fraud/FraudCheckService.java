package com.smartsys.fraud;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public record FraudCheckService(
    FraudCheckRepository fraudCheckRepository
) {
    public boolean isFraudulentCustomer(Integer customerId) {
        fraudCheckRepository.save(
            FraudCheck.builder()
                .customerId(customerId)
                .isFraudster(false)
                .createdAt(LocalDateTime.now())
                .build()
        );
        return false;
    }
}
