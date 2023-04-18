package com.hotel.app.service.impl;

import com.hotel.app.service.CustomerService;
import com.hotel.app.models.Customer;
import com.hotel.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    public Customer getById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
    }
    @Override
    public Customer getByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
    }
    @Override
    public Customer getByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
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
    public void updateCustomer(Customer mainCustomer) {
        Customer customer = customerRepository.findById(mainCustomer.getId()).orElse(null);

        if (customer != null) {
            if((customer.getEmail().equals(mainCustomer.getEmail()) || !existEmail(mainCustomer.getEmail())) &&
                    (customer.getPhoneNumber().equals(mainCustomer.getPhoneNumber()) || !existPhoneNumber(mainCustomer.getPhoneNumber()))) {
                customer.setFullName(mainCustomer.getFullName());
                customer.setEmail(mainCustomer.getEmail());
                customer.setPhoneNumber(mainCustomer.getPhoneNumber());
                customerRepository.save(customer);
            }
        }
    }
    @Override
    public Boolean existPhoneNumber(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElse(null);
        if (customer != null) {
            return true;
        }
        return false;
    }
    @Override
    public Boolean existEmail(String email) {
        Customer customer = customerRepository.findByEmail(email).orElse(null);
        if (customer != null) {
            return true;
        }
        return false;
    }
}
