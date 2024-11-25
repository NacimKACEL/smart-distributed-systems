package com.smartsys.customer;

import org.springframework.stereotype.Service;

import com.smartsys.clients.fraud.FraudCheckResponse;
import com.smartsys.clients.fraud.FraudClient;
import com.smartsys.clients.notification.NotificationClient;
import com.smartsys.clients.notification.NotificationRequest;

@Service
public record CustomerService(
    CustomerRepository customerRepository,
    FraudClient fraudClient,
    NotificationClient notificationClient) {
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

        notificationClient.sendNotification(
                new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Amigoscode...",
                        customer.getFirstName())
        ));
    }

}
