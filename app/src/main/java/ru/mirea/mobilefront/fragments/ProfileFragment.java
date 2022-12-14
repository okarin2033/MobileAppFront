package ru.mirea.mobilefront.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import ru.mirea.mobilefront.MainActivity;
import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.service.AuthService;
import ru.mirea.mobilefront.service.UserService;
import ru.mirea.mobilefront.service.UserSession;

public class ProfileFragment extends Fragment {

    boolean phoneButtonPressed = false;
    boolean addressButtonPressed = false;

    MutableLiveData<String> tokenData = AuthService.getLiveData();
    MutableLiveData<UserSession> userSession = UserSession.getUserSession();

    UserService userService = new UserService();

    ImageButton imageButton;
    TextView username;

    TextView phoneText;
    TextView addressText;

    EditText editPhoneText;
    EditText editAddressText;

    ImageButton editAddressButton;
    ImageButton editPhoneButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return (ViewGroup)inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        imageButton = (ImageButton) view.findViewById(R.id.logout_button);
        username = (TextView) view.findViewById(R.id.username_interface);
        phoneText = (TextView) view.findViewById(R.id.phone_text);
        addressText = (TextView) view.findViewById(R.id.address_text);

        editAddressButton = (ImageButton) view.findViewById(R.id.edit_address_button);
        editPhoneButton = (ImageButton) view.findViewById(R.id.edit_phone_button);
        editAddressText = (EditText) view.findViewById(R.id.edit_address_text);
        editPhoneText = (EditText) view.findViewById(R.id.edit_phone_text);


        Intent intent = new Intent(getActivity(), MainActivity.class);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tokenData.postValue("Logout");
                startActivity(intent);
                username.setText("???? ??????????????????????");
            }
        });

        userSession.observe(getViewLifecycleOwner(), new Observer<UserSession>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(UserSession userSession) {
                username.setText(userSession.getUsername());
                if (userSession.getPhone() == null || userSession.getPhone().equals(""))
                    phoneText.setText("?????????????? ???? ????????????");
                else phoneText.setText("?????? ??????????????: " + userSession.getPhone());
                if (userSession.getAddress() == null || userSession.getAddress().equals(""))
                    addressText.setText("?????????? ???? ????????????");
                else addressText.setText("?????? ??????????: "+ userSession.getAddress());
            }
        });

        //?????????????????? ???????????? ????????????????????????
        {

            editPhoneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    phoneButtonPressed = !phoneButtonPressed;
                    if (phoneButtonPressed){
                        editPhoneText.setVisibility(View.VISIBLE);
                        phoneText.setVisibility(View.INVISIBLE);
                        editPhoneButton.setImageResource(R.drawable.confirm_changes);
                    } else {
                        editPhoneText.setVisibility(View.INVISIBLE);
                        phoneText.setVisibility(View.VISIBLE);
                        editPhoneButton.setImageResource(R.drawable.edit_image);

                        userService.updateUserPhone(editPhoneText.getText().toString());
                    }
                }
            });

            editAddressButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addressButtonPressed = !addressButtonPressed;
                    if (addressButtonPressed){
                        editAddressText.setVisibility(View.VISIBLE);
                        addressText.setVisibility(View.INVISIBLE);
                        editAddressButton.setImageResource(R.drawable.confirm_changes);
                    } else {
                        editAddressText.setVisibility(View.INVISIBLE);
                        addressText.setVisibility(View.VISIBLE);
                        editAddressButton.setImageResource(R.drawable.edit_image);

                        userService.updateUserAddress(editAddressText.getText().toString());

                    }
                }
            });


        }


    }

}


