package org.scalefocus.controller;

import org.scalefocus.exception.*;
import org.scalefocus.domain.User;
import org.scalefocus.domain.request.UserRequest;
import org.scalefocus.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity getUsers(@RequestParam(required = false) String username,
                                   @RequestParam(required = false) String email,
                                   @RequestParam(required = false) Integer id) {
        if (username != null) {
            User user = null;
            try {
                user = userService.getUserByUsername(username);
            } catch (UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.ok(user);
        }
        if (email != null) {
            User user = null;
            try {
                user = userService.getUserByEmail(email);
            } catch (UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.ok(user);
        }
        if (id != null) {
            User user = null;
            try {
                user = userService.getUserById(id);
            } catch (UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.ok(user);
        }
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserRequest userRequest) {
        try {
            userService.createUser(userRequest.getUsername(), userRequest.getEmail(),
                    userRequest.getPhone(), userRequest.getCity());
        } catch (InvalidCityException | InvalidUsernameException | InvalidPhoneNumberFormatException |
                 InvalidEmailException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.status(201).build();
    }
}