package com.hotel.app.service;

import com.hotel.app.models.Customer;

import java.util.List;

public interface CustomerService {
    Customer getById(Integer id);
    Customer getByEmail(String email);
    Customer getByPhoneNumber(String phoneNumber);
    List<Customer> getAll();
    void save(Customer customer);
    void deleteById(Integer id);
    void updateCustomer(Integer id, String fullName, String email, String phoneNumber);
}
