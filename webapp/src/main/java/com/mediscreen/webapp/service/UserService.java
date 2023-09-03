package com.mediscreen.webapp.service;

import com.mediscreen.webapp.exception.LoginAlreadyExistsException;
import com.mediscreen.webapp.exception.LoginNotFoundException;
import com.mediscreen.webapp.model.User;
import com.mediscreen.webapp.model.UserCreate;
import com.mediscreen.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveNewUser(UserCreate userCreate) {

        if(repository.findByLogin(userCreate.getLogin()).isPresent()){
            throw new LoginAlreadyExistsException();
        }
        repository.save(convertToUserWithEncryptedPassword(userCreate));
    }

    @Override
    public Page<User> getAllUsersPaginated(int pageNumber, int itemPerPage) {
        return repository.findAll(PageRequest.of(pageNumber, itemPerPage));
    }

    @Override
    public User getUser(String login) {
        return repository.findByLogin(login).orElseThrow(LoginNotFoundException::new);
    }

    @Override
    public void updateUser(User userUpdate) {
        User user = getUser(userUpdate.getLogin());
        user.setRole(userUpdate.getRole());
        if (userUpdate.getPassword() != null && userUpdate.getPassword() != "") {
            user.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        }
        repository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String login) {
        getUser(login);
        repository.deleteByLogin(login);
    }

    private User convertToUserWithEncryptedPassword(UserCreate userCreate) {
        User user = new User();
        user.setLogin(userCreate.getLogin());
        user.setPassword(passwordEncoder.encode(userCreate.getPassword()));
        user.setRole(userCreate.getRole());
        user.setCreationDate(LocalDate.now());
        return user;
    }
}