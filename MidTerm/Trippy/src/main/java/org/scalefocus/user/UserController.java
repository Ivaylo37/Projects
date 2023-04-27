package org.scalefocus.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> printAllUsers(){
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }
    @PostMapping("/users")
    public ResponseEntity<Void> addUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest.getUsername(), userRequest.getEmail(),
                userRequest.getPhone(), userRequest.getCity());
        return ResponseEntity.status(201).build();
    }
}
