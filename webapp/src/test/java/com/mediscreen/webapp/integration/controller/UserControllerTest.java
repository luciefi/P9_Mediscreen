package com.mediscreen.webapp.integration.controller;

import com.mediscreen.webapp.exception.UserClientException;
import com.mediscreen.webapp.model.User;
import com.mediscreen.webapp.model.UserCreate;
import com.mediscreen.webapp.service.IUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
public class UserControllerTest {

    @MockBean
    IUserService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUserList() throws Exception {
        // Arrange
        User user = new User();
        when(service.getAllUsersPaginated(anyInt(), anyInt())).thenReturn(new PageImpl<>(Collections.singletonList(user)));

        // Act
        mockMvc.perform(get("/user/list").param("page", "2")).andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).getAllUsersPaginated(1, 3);
    }

    @Test
    public void addUserTest() throws Exception {
        mockMvc.perform(get("/user/add")).andDo(print()).andExpect(status().isOk()).andExpect(view().name("addUser")).andExpect(content().string(containsString("Add New User")));
    }

    @Test
    public void addUserPostTest() throws Exception {
        mockMvc.perform(post("/user/add").contentType(MediaType.APPLICATION_FORM_URLENCODED).content("login=login&password=password&role=ROLE_USER")).andDo(print()).andExpect(status().isFound()).andExpect(view().name("redirect:/user/list"));
        verify(service, Mockito.times(1)).saveNewUser(any(UserCreate.class));
    }

    @Test
    public void addUserPostFormErrorTest() throws Exception {
        mockMvc.perform(post("/user/add").contentType(MediaType.APPLICATION_FORM_URLENCODED).content("login=login&password=password&role=ROLE")).andDo(print()).andExpect(status().isOk()).andExpect(view().name("addUser"));
        verify(service, Mockito.never()).saveNewUser(any(UserCreate.class));
    }

    @Test
    public void userDetails() throws Exception {
        User user = new User();
        when(service.getUser(anyString())).thenReturn(user);
        mockMvc.perform(get("/user/details/login")).andDo(print()).andExpect(status().isOk()).andExpect(view().name("userDetails"));
        verify(service, Mockito.times(1)).getUser("login");
    }

    @Test
    public void updateUserForm() throws Exception {
        User user = new User();
        when(service.getUser(anyString())).thenReturn(user);
        mockMvc.perform(get("/user/update/login")).andDo(print()).andExpect(status().isOk()).andExpect(view().name("updateUser")).andExpect(content().string(containsString("Update User")));
        verify(service, Mockito.times(1)).getUser("login");
    }

    @Test
    public void updateUserFormNotFound() throws Exception {
        when(service.getUser(anyString())).thenThrow(UserClientException.class);
        mockMvc.perform(get("/user/update/login")).andDo(print()).andExpect(status().isFound()).andExpect(view().name("redirect:/user/list"));
        verify(service, Mockito.times(1)).getUser("login");
    }

    @Test

    public void updateUserPostTest() throws Exception {
        mockMvc.perform(post("/user/update/login").contentType(MediaType.APPLICATION_FORM_URLENCODED).content("login=login&password=&role=ROLE_USER")).andDo(print()).andExpect(status().isFound()).andExpect(view().name("redirect:/user/list"));
        verify(service, Mockito.times(1)).updateUser(any(User.class));
    }

    @Test
    public void updateUserPostFormErrorTest() throws Exception {
        mockMvc.perform(post("/user/update/login").contentType(MediaType.APPLICATION_FORM_URLENCODED).content("login=login&password=password&role=ROLE")).andDo(print()).andExpect(status().isOk()).andExpect(view().name("updateUser"));
        verify(service, Mockito.never()).updateUser(any(User.class));
    }

    @Test
    public void deleteUserForm() throws Exception {
        User user = new User();
        when(service.getUser(anyString())).thenReturn(user);
        mockMvc.perform(get("/user/delete/login")).andDo(print()).andExpect(status().isOk()).andExpect(view().name("deleteUser")).andExpect(content().string(containsString("Delete User")));
        verify(service, Mockito.times(1)).getUser("login");
    }

    @Test
    public void deleteUserFormNotFound() throws Exception {
        when(service.getUser(anyString())).thenThrow(UserClientException.class);
        mockMvc.perform(get("/user/delete/login")).andDo(print()).andExpect(status().isFound()).andExpect(view().name("redirect:/user/list"));
        verify(service, Mockito.times(1)).getUser("login");
    }

    @Test
    public void deleteUserPostTest() throws Exception {
        mockMvc.perform(post("/user/delete/login")).andDo(print()).andExpect(status().isFound()).andExpect(view().name("redirect:/user/list"));
        verify(service, Mockito.times(1)).deleteUser("login");
    }
}
