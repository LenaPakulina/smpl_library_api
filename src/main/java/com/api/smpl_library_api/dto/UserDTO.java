package com.api.smpl_library_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDTO {

    @NotBlank(message = "login не может быть пустым")
    private String login;

    @NotBlank(message = "password не может быть пустым")
    private String password;

    @NotBlank(message = "name не может быть пустым")
    private String name;
}

