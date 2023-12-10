package com.sersun.trello_app.service;

import com.sersun.trello_app.model.User;
import com.sersun.trello_app.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    public void createUser(User user) {
        usersRepository.save(user);
        log.info("Created new user: " + user);
    }

    public User returnUserById(Integer id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public void updateUser(Integer id, User user) {
        User foundUser = usersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        user.setUserId(id);
        usersRepository.save(user);
        log.info("Updated user with id=" + id + " : " + user);
    }

    public User updateUserPassword(Integer id, String newPassword) {
        User user = usersRepository.findById(id).orElse(null);
        if (user != null) {
            user.setPassword(newPassword);
            return usersRepository.save(user);
        }
        return null;
    }


    public void deleteUser(Integer id) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        usersRepository.delete(user);
        log.info("User with id=" + id + " has been deleted!");
    }
}
