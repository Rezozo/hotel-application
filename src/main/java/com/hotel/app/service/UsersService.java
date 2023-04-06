package com.hotel.app.service;

import com.hotel.app.enums.Role;
import com.hotel.app.models.Users;

import java.util.List;

public interface UsersService {
    Users getByEmail(String email);
    List<Users> getAll();
    void save(Users users);
    void deleteByEmail(String email);
    void updateByEmail(String email, String fullName, String password);
    void updateUsersGroup(String email, Role groups);
}
