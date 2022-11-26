package ru.mirea.mobilefront.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class UserDto {
    private String username;
    private String email;
    private RoleEnum role;
    private String address;
    private String phone;
}
