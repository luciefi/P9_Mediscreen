package com.mediscreen.webapp.service;

import com.mediscreen.webapp.exception.LoginNotFoundException;
import com.mediscreen.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws LoginNotFoundException {
        com.mediscreen.webapp.model.User user = userRepository.findByLogin(login).orElseThrow(LoginNotFoundException::new);
        return new User(user.getLogin(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }
}
