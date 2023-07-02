package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Override
    public void saveNewUser(User User) {

    }

    @Override
    public Page<User> getAllUsersPaginated(int pageNumber, int itemPerPage) {
        User user = new User();
        user.setLogin("myUser");
        user.setPassword("password");
        user.setRole("user");

        User admin = new User();
        admin.setLogin("myAdmin");
        admin.setPassword("password");
        admin.setRole("admin");

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(admin);

        return new PageImpl<>(userList);
    }

    @Override
    public User getUser(String login) {
        User user = new User();
        user.setLogin("myUser");
        user.setPassword("password");
        user.setRole("user");
        return user;
    }

    @Override
    public void updateUser(User User) {

    }

    @Override
    public void deleteUser(String id) {

    }
}
