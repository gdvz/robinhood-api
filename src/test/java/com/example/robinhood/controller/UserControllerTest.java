package com.example.robinhood.controller;

import com.example.robinhood.entity.User;
import com.example.robinhood.model.UserRoleModel;
import com.example.robinhood.model.request.UserDataRequestModel;
import com.example.robinhood.service.UserService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testGetDataUser() {
        List<User> users = Collections.singletonList(new User());

        when(userService.findAll()).thenReturn(users);

        ResponseEntity<List<User>> responseEntity = userController.getDataUser();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(users, responseEntity.getBody());

        verify(userService, times(1)).findAll();
    }

    @Test
    public void testRegisterUser() {

        UserDataRequestModel request = new UserDataRequestModel();
        request.setUsername("johngood");
        request.setEmail("john@example.com");
        request.setFullName("John Good");
        request.setPassword("1234");
        request.setAppUserRole(UserRoleModel.ROLE_USER);

        User user = new User();
        user.setFullName("John Doe");
        user.setEmail("john@example.com");

        when(userService.registerNewUser(any())).thenReturn(user);

        ResponseEntity<User> responseEntity = userController.registerUser(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());

        verify(userService, times(1)).registerNewUser(any());
    }

    @Test
    public void testLimitHandler() {

        RequestNotPermitted exception = mock(RequestNotPermitted.class);

        ResponseEntity<String> responseEntity = userController.limitHandler(exception);

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, responseEntity.getStatusCode());
        assertEquals("Too many Request", responseEntity.getBody());
    }
}
