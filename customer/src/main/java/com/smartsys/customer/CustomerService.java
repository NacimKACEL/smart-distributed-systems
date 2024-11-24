package com.smartsys.customer;

import org.springframework.stereotype.Service;

import com.smartsys.clients.FraudCheckResponse;
import com.smartsys.clients.FraudClient;

@Service
public record CustomerService(
    CustomerRepository customerRepository,
    FraudClient fraudClient) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
            .firstName(customerRegistrationRequest.firstName())
            .lastName(customerRegistrationRequest.lastName())
            .email(customerRegistrationRequest.email())
            .build();
        //todo: check valid name
        //todo: check valid email
        customerRepository.saveAndFlush(customer); 

        FraudCheckResponse fraudCheckResponse =
                fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
    }

}
