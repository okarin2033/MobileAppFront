package ru.mirea.mobilefront.service;

import lombok.Data;
import ru.mirea.mobilefront.dto.RoleEnum;

@Data
public class UserSession {
    private static UserSession userSession = new UserSession();
    private String token;
    private String username;
    private String email;
    private RoleEnum role;

    public static UserSession getUserSession(){
        return userSession;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
