package com.mediscreen.webapp.controller;

import com.mediscreen.webapp.exception.LoginAlreadyExistsException;
import com.mediscreen.webapp.exception.UserClientException;
import com.mediscreen.webapp.exception.LoginNotFoundException;
import com.mediscreen.webapp.model.User;
import com.mediscreen.webapp.model.UserCreate;
import com.mediscreen.webapp.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final int USER_PER_PAGE = 3;
    @Autowired
    private IUserService service;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/add")
    public String createUser(Model model) {
        UserCreate userCreate = new UserCreate();
        model.addAttribute("userCreate", userCreate);
        return "addUser";
    }

    @PostMapping("/add")
    public String saveNewUser(@Valid UserCreate userCreate, BindingResult result) {
        if (result.hasErrors()) {
            logger.info("Cannot add user : invalid form");
            return "addUser";
        }
        try {
            service.saveNewUser(userCreate);
            logger.info("New user added");
            return "redirect:/user/list";
        } catch (LoginAlreadyExistsException e) {
            logger.info("Cannot add user : " + e.getMessage());
            ObjectError error = new ObjectError("globalError", "Cannot add user : " + e.getMessage());
            result.addError(error);
            return "addUser";
        }
    }

    @GetMapping("/list")
    public String getUserList(Model model, @RequestParam(name = "page", required = false) Optional<Integer> pageNumber) {
        int currentPage = Math.max(pageNumber.orElse(1), 1);
        Page<User> userPage = service.getAllUsersPaginated(currentPage - 1, USER_PER_PAGE);

        model.addAttribute("userPage", userPage);

        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "userList";
    }

    @GetMapping("/details/{login}")
    public String getDetailsUserForm(@PathVariable("login") String login, Model model) {
        try {
            User user = service.getUser(login);
            model.addAttribute("user", user);
            return "userDetails";
        } catch (UserClientException e) {
            logger.info("Cannot get user details : " + e.getMessage());
            return "redirect:/user/list";
        }
    }

    @GetMapping("/update/{login}")
    public String updateUserForm(@PathVariable("login") String login, @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer, Model model) {
        try {
            User user = service.getUser(login);
            model.addAttribute("user", user);
            String cancelUrl = referrer != null && referrer.contains("/user/details/") ? "/user/details/" + login : "/user/list";
            model.addAttribute("cancelUrl", cancelUrl);

            return "updateUser";
        } catch (UserClientException e) {
            logger.info("Cannot update user : " + e.getMessage());
            return "redirect:/user/list";
        }
    }

    @PostMapping("/update/{login}")
    public String updateUser(@PathVariable("login") String login, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            logger.info("Cannot update user : invalid form");
            return "updateUser";
        }
        service.updateUser(user);
        logger.info("User with login: " + login + " updated");
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{login}")
    public String deleteUserForm(@PathVariable("login") String login, Model model) {
        try {
            User user = service.getUser(login);
            model.addAttribute("user", user);
            return "deleteUser";
        } catch (UserClientException e) {
            logger.info("Cannot delete user : " + e.getMessage());
            return "redirect:/user/list";
        }
    }

    @PostMapping("/delete/{login}")
    public String deleteUser(@PathVariable("login") String login) {
        service.deleteUser(login);
        logger.info("User with login: " + login + " deleted");
        return "redirect:/user/list";
    }

    @ExceptionHandler({LoginNotFoundException.class})
    public String handleNotFoundException(Exception e) {
        logger.error("Not found exception: {}", e.getMessage());
        return "redirect:/user/list";
    }
}
