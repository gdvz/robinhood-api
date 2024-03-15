package com.example.robinhood.model.request;

import com.example.robinhood.model.UserRoleModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDataRequestModel {

    @NotBlank(message = "username is mandatory")
    private String username;

    @NotBlank(message = "email is mandatory")
    private String email;

    @NotBlank(message = "fullName is mandatory")
    private String fullName;

    @NotBlank(message = "password is mandatory")
    private String password;

    @NotNull(message = "appUserRole is mandatory")
    private UserRoleModel appUserRole;
}
