package com.sersun.trello_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sersun.trello_app.controller.UserController;
import com.sersun.trello_app.model.User;
import com.sersun.trello_app.service.UsersService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    User user;
    @InjectMocks
    private UserController userController;
    @Mock
    private UsersService usersService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
        user = User.builder()
                .userId(1684646)
                .username("testUsername")
                .password("testPassword")
                .email("testname@testmail.com")
                .build();
    }

    @Test
    public void testCreateUserEndpoint() throws Exception {

        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/register")
                                .contentType("application/json")
                                .content(userJson))
                .andExpect(status().isOk()
                );
        verify(usersService, Mockito.times(1)).createUser(user);
    }

    @Test
    public void testReturnUserByIdEndpoint() throws Exception {
        final Integer id = 1684646;

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/user/profile/" + id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
                );
        verify(usersService, Mockito.times(1)).returnUserById(1684646);
    }

    @Test
    public void testUpdateUserEndpoint() throws Exception {
        final Integer id = 1684646;
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/user/profile/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userJson))
                .andExpect(status().isOk()
                );
        verify(usersService, Mockito.times(1)).updateUser(id, user);
    }


    @Test
    public void testUpdateUserPasswordEndpoint() throws Exception {
        final Integer id = 1684646;
        final String newPassword = "testPassword2";
        User mockUser = new User();

        when(usersService.updateUserPassword(eq(id), any(String.class))).thenReturn(mockUser);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/user/" + id + "/password")
                                .content(newPassword)
                                .contentType("plain/text"))
                .andExpect(status().isOk())
                .andExpect(content().string("User with id: " + id + " has been updated"));

        verify(usersService).updateUserPassword(eq(id), any(String.class));
        verify(usersService, Mockito.times(1)).updateUserPassword(eq(id), eq(newPassword));
        // Mockito.verify(usersService, Mockito.times(1)).updateUserPassword(id,  newPassword);
    }

    @Test
    public void testDeleteUserEndpoint() throws Exception {
        final Integer id = 1684646;

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/user/profile/" + id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
                );
        verify(usersService, Mockito.times(1)).deleteUser(id);
    }


}

