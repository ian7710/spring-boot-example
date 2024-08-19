package com.example.controller;

import com.example.dto.UserDTO;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsersSorted(
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {

        Comparator<UserDTO> comparator;

        switch (sortBy.toLowerCase()) {
            case "email":
                comparator = Comparator.comparing(UserDTO::getEmail);
                break;
            case "id":
                comparator = Comparator.comparing(UserDTO::getId);
                break;
            case "name":
            default:
                comparator = Comparator.comparing(UserDTO::getName);
                break;
        }

        if (order.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        List<UserDTO> sortedUsers = userService.getAllUsersSorted(comparator);
        return new ResponseEntity<>(sortedUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO)
                .thenApply(user -> new ResponseEntity<>(user, HttpStatus.CREATED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
