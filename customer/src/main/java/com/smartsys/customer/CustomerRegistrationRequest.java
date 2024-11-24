package com.smartsys.customer;
 
public record CustomerRegistrationRequest(
    String firstName,
    String lastName,
    String email
) {}
