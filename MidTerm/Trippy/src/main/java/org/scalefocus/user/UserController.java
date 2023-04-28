package org.scalefocus.user;

import org.scalefocus.customExceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity printAllUsers(@RequestParam(required = false) String username,
                                        @RequestParam(required = false) String email,
                                        @RequestParam(required = false) int id)
    {
        if (username != null){
            User user = null;
            try {
                user = userService.findUserByUsername(username);
            } catch (UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.ok(user);
        }
        if (email != null){
            User user = null;
            try {
                user = userService.findUserByEmail(email);
            } catch (UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.ok(user);
        }
        if (id > 0){
            User user = null;
            try {
                user = userService.findUserById(id);
            } catch (UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.ok(user);
        }
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
