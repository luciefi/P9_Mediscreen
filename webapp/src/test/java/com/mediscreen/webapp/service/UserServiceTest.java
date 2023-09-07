package com.mediscreen.webapp.service;

import com.mediscreen.webapp.exception.LoginAlreadyExistsException;
import com.mediscreen.webapp.exception.LoginNotFoundException;
import com.mediscreen.webapp.model.User;
import com.mediscreen.webapp.model.UserCreate;
import com.mediscreen.webapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;


    @Test
    void saveNewUser() {
        // Arrange
        UserCreate user = new UserCreate();
        user.setLogin("login");
        user.setPassword("password");
        when(repository.findByLogin(anyString())).thenReturn(Optional.empty());

        // Act
        service.saveNewUser(user);

        // Assert
        verify(repository, times(1)).findByLogin("login");
        verify(repository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    void saveNewUser_AlreadyExists() {
        // Arrange
        UserCreate user = new UserCreate();
        user.setLogin("login");
        when(repository.findByLogin(anyString())).thenReturn(Optional.of(new User()));

        // Act
        assertThrows(LoginAlreadyExistsException.class, () -> service.saveNewUser(user));

        // Assert
        verify(repository, times(1)).findByLogin("login");
        verify(repository, times(0)).save(any(User.class));
    }

    @Test
    void getAllUsersPaginated() {
        // Act
        Page<User> user = service.getAllUsersPaginated(0, 3);

        // Assert
        verify(repository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getUser() {
        // Arrange
        User user = new User();
        when(repository.findByLogin(anyString())).thenReturn(Optional.of(user));

        // Act
        service.getUser("login");

        // Assert
        verify(repository, times(1)).findByLogin("login");
    }

    @Test
    void getUser_NotFound() {
        // Arrange
        when(repository.findByLogin(anyString())).thenReturn(Optional.empty());

        // Act
        assertThrows(LoginNotFoundException.class, () -> service.getUser("login"));

        // Assert
        verify(repository, times(1)).findByLogin("login");
    }

    @Test
    void updateUser_samePasswordNull() {
        // Arrange
        User user = new User();
        user.setLogin("login");
        when(repository.findByLogin(anyString())).thenReturn(Optional.of(user));

        // Act
        service.updateUser(user);

        // Assert
        verify(repository, times(1)).findByLogin("login");
        verify(repository, times(1)).save(user);
        verify(passwordEncoder, times(0)).encode(anyString());
    }

    @Test
    void updateUser_samePasswordEmpty() {
        // Arrange
        User user = new User();
        user.setLogin("login");
        user.setPassword("");
        when(repository.findByLogin(anyString())).thenReturn(Optional.of(user));

        // Act
        service.updateUser(user);

        // Assert
        verify(repository, times(1)).findByLogin("login");
        verify(repository, times(1)).save(user);
        verify(passwordEncoder, times(0)).encode(anyString());
    }

    @Test
    void updateUser_newPassword() {
        // Arrange
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        when(repository.findByLogin(anyString())).thenReturn(Optional.of(user));

        // Act
        service.updateUser(user);

        // Assert
        verify(repository, times(1)).findByLogin("login");
        verify(repository, times(1)).save(user);
        assertNull(user.getPassword());
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    void deleteUser() {
        // Arrange
        User user = new User();
        when(repository.findByLogin(anyString())).thenReturn(Optional.of(user));

        // Act
        service.deleteUser("login");

        // Assert
        verify(repository, times(1)).findByLogin("login");
        verify(repository, times(1)).deleteByLogin("login");
    }
}
