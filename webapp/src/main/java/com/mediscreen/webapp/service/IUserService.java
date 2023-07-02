package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.User;
import org.springframework.data.domain.Page;

public interface IUserService {
    void saveNewUser(User User);

    Page<User> getAllUsersPaginated(int pageNumber, int itemPerPage);

    User getUser(String l);

    void updateUser(User User);

    void deleteUser(String id);
}
