package com.hotel.app.service.impl;

import com.hotel.app.service.CustomerService;
import com.hotel.app.models.Customer;
import com.hotel.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    public Customer getById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }
    @Override
    public Customer getByEmail(String email) {
        return customerRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Customer getByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    @Override
    public List<Customer> getAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }
    @Override
    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }
    @Override
    public void updateCustomer(Integer id, String fullName, String email, String phoneNumber) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer != null) {
            customer.setFullName(fullName);
            customer.setEmail(email);
            customer.setPhoneNumber(phoneNumber);
            customerRepository.save(customer);
        }
    }
}
