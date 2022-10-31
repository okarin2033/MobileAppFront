package ru.mirea.mobilefront.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDto {
    String token;
    RoleEnum role;
    public TokenDto(String token, RoleEnum role) {
        this.token = token;
        this.role = role;
    }
}
