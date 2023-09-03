package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.User;
import com.mediscreen.webapp.model.UserCreate;
import org.springframework.data.domain.Page;

public interface IUserService {
    void saveNewUser(UserCreate userCreate);

    Page<User> getAllUsersPaginated(int pageNumber, int itemPerPage);

    User getUser(String l);

    void updateUser(User User);

    void deleteUser(String id);
}
