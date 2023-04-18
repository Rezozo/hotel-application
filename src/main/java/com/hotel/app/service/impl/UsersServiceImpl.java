package com.hotel.app.service.impl;

import com.hotel.app.enums.Role;
import com.hotel.app.service.UsersService;
import com.hotel.app.models.Users;
import com.hotel.app.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;
    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    @Override
    public Users getByEmail(String email) {
        return usersRepository.findByEmail(email).orElse(null);
    }
    @Override
    public List<Users> getAll() {
        return (List<Users>) usersRepository.findAll();
    }

    @Override
    public void save(Users users) {
        usersRepository.save(users);
    }

    @Override
    public void deleteById(Integer id) {
        usersRepository.deleteById(id);
    }

    @Override
    public void deleteByEmail(String email) {
        usersRepository.deleteByEmail(email);
    }

    @Override
    public void updateById(Integer id, String email, String fullName) {
        Users users = usersRepository.findById(id).orElse(null);
        if (users != null) {
            users.setEmail(email);
            users.setFullName(fullName);
            usersRepository.save(users);
        }
    }

    @Override
    public void updateByEmail(String email, String fullName, String password) {
        Users users = usersRepository.findByEmail(email).orElse(null);
        if(users != null) {
            users.setFullName(fullName);
            users.setEmail(email);
            users.setPassword(password);
            usersRepository.save(users);
        }
    }

    @Override
    public void updateUsersGroup(Integer id, Role groups) {
        Users users = usersRepository.findById(id).orElse(null);
        if (users != null) {
            users.setGroups(groups);
            usersRepository.save(users);
        }
    }
}
