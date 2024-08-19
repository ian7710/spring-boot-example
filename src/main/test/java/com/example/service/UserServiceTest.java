package main.test.java.com.example.service;

import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.exception.UserNotFoundException;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserById_UserExists() {
        User user = new User("John Doe", "john@example.com");
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO userDTO = userService.getUserById(1L);

        assertNotNull(userDTO);
        assertEquals("John Doe", userDTO.getName());
    }

    @Test
    public void testGetUserById_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    // Additional tests for other service methods
}
