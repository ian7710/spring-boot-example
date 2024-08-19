package main.java.com.example.service;

import com.example.dto.UserDTO;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    CompletableFuture<UserDTO> createUser(UserDTO userDTO);

    CompletableFuture<UserDTO> updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);

    CompletableFuture<Void> assignRoleToUser(Long userId, Long roleId);

    CompletableFuture<Void> removeRoleFromUser(Long userId, Long roleId);
}
