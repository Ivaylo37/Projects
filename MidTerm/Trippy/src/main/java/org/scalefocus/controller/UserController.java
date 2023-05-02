package org.scalefocus.controller;

import org.scalefocus.model.User;
import org.scalefocus.model.request.UserRequest;
import org.scalefocus.service.UserService;
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
            return ResponseEntity.ok(userService.getUserByUsername(username));
        }
        if (email != null) {
            return ResponseEntity.ok(userService.getUserByEmail(email));
        }
        if (id != null) {
            return ResponseEntity.ok(userService.getUserById(id));
        }
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserRequest userRequest) {
            userService.createUser(userRequest.getUsername(), userRequest.getEmail(),
                    userRequest.getPhone(), userRequest.getCity());
        return ResponseEntity.status(201).build();
    }
}