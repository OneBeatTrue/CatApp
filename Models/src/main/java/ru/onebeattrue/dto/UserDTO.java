package ru.onebeattrue.dto;

import ru.onebeattrue.entities.user.User;
import ru.onebeattrue.enums.Role;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public record UserDTO(
        @Min(0) Long id,
        @Size(min = 2, max = 20)
        @NotBlank(message = "Username is required") String username,
        @Size(min = 8, max = 30)
        @NotBlank(message = "Password is required") String password,
        @Min(1) Long master,
        @NotNull(message = "Role is required") Role role
) implements Serializable {
    public UserDTO(User user){
        this(user.getId(), user.getUsername(), user.getPassword(), user.getMaster(), user.getRole());
    }
    public UserDTO(String username, String password, Long master, Role role){
        this(0L, username, password, master, role);
    }
}
