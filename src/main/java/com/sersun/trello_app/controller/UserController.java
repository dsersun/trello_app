package com.sersun.trello_app.controller;

import com.sersun.trello_app.model.User;
import com.sersun.trello_app.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/*
* Pentru a nu dezvolta autoriarea s-a efectuat adaugarea la endpointuri PathVariable {id}
* */
@RestController
public class UserController {

    @Autowired
    UsersService usersService;

    @PostMapping("/api/register")
    public ResponseEntity<String> registerNewUser(@RequestBody @Valid User user){
        usersService.createUser(user);
        return ResponseEntity.ok("User " + user + " has been created");
    }

    @GetMapping("/api/user/profile/{id}")
    public ResponseEntity<User> returnUserById(@PathVariable Integer id){
        return ResponseEntity.ok(usersService.returnUserById(id));
    }

    @PutMapping("/api/user/profile/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody User user){
        usersService.updateUser(id, user);
        return ResponseEntity.ok("Employee with id " + id + " has been updated!");
    }

    @PutMapping("/api/user/{id}/password")
    public ResponseEntity<String> updateUserPassword(@PathVariable Integer id, @RequestBody String newPassword){
        usersService.updateUserPassword(id, newPassword);
        return ResponseEntity.ok("User with id: " + id + " has been updated");
    }

    @DeleteMapping("/api/user/profile/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id){
        usersService.deleteUser(id);
        return ResponseEntity.ok("User with id: " + id + " has been deleted!");
    }
}
