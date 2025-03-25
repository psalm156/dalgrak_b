package springbootApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springbootApplication.domain.User;
import springbootApplication.dto.AddUserRequest;
import springbootApplication.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userId(1L)
                .username("testUser")
                .email("test@example.com")
                .password("password123")
                .build();
    }

    @Test
    void testSaveUser() {
        AddUserRequest request = new AddUserRequest("test@example.com", "password123");

        when(bCryptPasswordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        Long userId = userService.save(request);

        assertNotNull(userId);
        assertEquals(1L, userId);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> retrievedUsers = userService.getAllUsers();

        assertEquals(1, retrievedUsers.size());
        assertEquals("test@example.com", retrievedUsers.get(0).getEmail());
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserById(2L);

        assertFalse(foundUser.isPresent());
    }

    @Test
    void testCreateUser() {
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("hashedPassword");
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertNotNull(createdUser);
        assertEquals("testUser", capturedUser.getUsername());
    }

    @Test
    void testUpdateUser_Success() {
        User updatedDetails = User.builder()
                .username("updatedUser")
                .email("updated@example.com")
                .password("newPassword")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.encode(updatedDetails.getPassword())).thenReturn("hashedNewPassword");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User userToSave = invocation.getArgument(0);
            userToSave.setUserId(1L); 
            return userToSave;
        });

        User updatedUser = userService.updateUser(1L, updatedDetails);

        assertNotNull(updatedUser);
        assertEquals("updatedUser", updatedUser.getUsername());
        assertEquals("updated@example.com", updatedUser.getEmail());

        verify(userRepository).save(captor.capture());
        User capturedUser = captor.getValue();
        assertEquals("updatedUser", capturedUser.getUsername());
        assertEquals("updated@example.com", capturedUser.getEmail());
    }

    @Test
    void testUpdateUser_NotFound() {
        User updatedDetails = User.builder().username("updatedUser").build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.updateUser(1L, updatedDetails)
        );

        assertEquals("User not found with id: 1", exception.getMessage());
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUser(1L));
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.deleteUser(1L)
        );

        assertEquals("User not found with id: 1", exception.getMessage());
    }
}

