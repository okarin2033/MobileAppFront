package ru.mirea.mobilefront.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.sql.SQLOutput;

import ru.mirea.mobilefront.MainActivity;
import ru.mirea.mobilefront.MenuActivity;
import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.service.AuthService;
import ru.mirea.mobilefront.service.UserSession;

public class profile_fragment extends Fragment {

    MutableLiveData<String> tokenData = AuthService.getLiveData();
    MutableLiveData<UserSession> userSession = UserSession.getUserSession();

    ImageButton imageButton;
    TextView username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (ViewGroup)inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        imageButton = (ImageButton) view.findViewById(R.id.logout_button);
        username = (TextView) view.findViewById(R.id.username_interface);

        Intent intent = new Intent(getActivity(), MainActivity.class);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tokenData.postValue("Logout");
                startActivity(intent);
                username.setText("Не авторизован");
            }
        });

        userSession.observe(getViewLifecycleOwner(), new Observer<UserSession>() {
            @Override
            public void onChanged(UserSession userSession) {
                username.setText(userSession.getUsername());
            }
        });



    }

}


