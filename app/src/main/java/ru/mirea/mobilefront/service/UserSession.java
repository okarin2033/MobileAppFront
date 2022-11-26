package ru.mirea.mobilefront.service;

import androidx.lifecycle.MutableLiveData;

import lombok.Data;
import ru.mirea.mobilefront.dto.RoleEnum;

@Data
public class UserSession {
    private static MutableLiveData<UserSession> userSession= new MutableLiveData<UserSession>();
    private String token;
    private String username;
    private String email;
    private String phone;
    private String address;
    private RoleEnum role;

    public static MutableLiveData<UserSession> getUserSession(){
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
