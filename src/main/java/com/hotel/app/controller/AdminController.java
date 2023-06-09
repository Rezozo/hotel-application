package com.hotel.app.controller;

import com.hotel.app.enums.Role;
import com.hotel.app.models.Users;
import com.hotel.app.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:8081/")
@AllArgsConstructor
public class AdminController {
    private UsersService usersService;
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<Users> getUsers() {
        return usersService.getAll();
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Integer userId) {
        usersService.deleteById(userId);
    }

    @RequestMapping(value = "/users/{userId}/roles", method = RequestMethod.PUT)
    public void updateUser(@PathVariable Integer userId) {
        usersService.updateUsersGroup(userId, Role.Manager);
    }
}
