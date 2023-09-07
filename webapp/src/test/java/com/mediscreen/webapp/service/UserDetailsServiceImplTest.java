package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.User;
import com.mediscreen.webapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserDetailsServiceImpl service;

    @Test
    void loadUserByUsername() {
        // Arrange
        User userFromRepository =  new User();
        userFromRepository.setLogin("login");
        userFromRepository.setPassword("password");
        userFromRepository.setRole("ROLE_USER");
        when(repository.findByLogin("login")).thenReturn(Optional.of(userFromRepository));

        // Act
        UserDetails user = service.loadUserByUsername("login");

        // Assert
        assertEquals("login",  user.getUsername());
        assertEquals("password",  user.getPassword());
        assertEquals(1,  user.getAuthorities().size());
        assertTrue( user.getAuthorities().contains(new SimpleGrantedAuthority( "ROLE_USER")));
    }
}