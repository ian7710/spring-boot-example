package com.example.service;

import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.exception.UserNotFoundException;
import com.example.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Cacheable("users")
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "user", key = "#id")
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return convertToDTO(user);
    }

    @Override
    @Async
    public CompletableFuture<UserDTO> createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        user = userRepository.save(user);

        // Send a message to RabbitMQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, "User created: " + user.getId());

        return CompletableFuture.completedFuture(convertToDTO(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Custom sorting function
    public List<UserDTO> getAllUsersSorted(Comparator<UserDTO> comparator) {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    // DTO to Entity conversion
    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    // Entity to DTO conversion
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
