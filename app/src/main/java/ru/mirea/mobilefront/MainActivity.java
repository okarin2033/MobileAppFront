package ru.mirea.mobilefront;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import pl.droidsonroids.gif.GifImageView;
import ru.mirea.mobilefront.service.AuthService;
import ru.mirea.mobilefront.service.UserService;

public class MainActivity extends AppCompatActivity {
    //Button loginButton;
    AppCompatButton loginButton;
    AppCompatButton loginButton1;
    EditText loginField;
    EditText passwordField;
    GifImageView gifImageView; // для гифки книжек

    Animation blink_anim;
    UserService userService = new UserService();
    AuthService authService = new AuthService();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Intent intent = new Intent(this, MenuActivity.class);
        //startActivity(intent);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //уведомление ошибки логина
        TextView errorTextLogin = (TextView) findViewById(R.id.login_error_text);

        //Логин юзера
        loginField = (EditText) findViewById(R.id.login_text);
        passwordField = (EditText) findViewById(R.id.password_text);
        loginButton = (AppCompatButton) findViewById(R.id.login_button);

        gifImageView = findViewById(R.id.gif_load_image);
        gifImageView.setVisibility(View.INVISIBLE);//тут гифка становиться не видмой
        blink_anim = AnimationUtils.loadAnimation(this, R.anim.blink_anim);


//        loginButton.setOnTouchListener(new View.OnTouchListener() {
//            @SuppressLint("ClickableViewAccessibility")
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
//                    loginButton.startAnimation(blink_anim);
//                    loginButton.clearAnimation();
//                }
//
//                return false;
//            }
//        });


                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        authService.login(loginField.getText().toString()
                                , passwordField.getText().toString());


                        gifImageView.setVisibility(View.VISIBLE);//тут видмой
                        //  Здесь врубается кнопка логина, загрузочка все такое ее надо будет вырубить
                        //  через секунд 10 в случае безуспешности логина и все такое
                        //Старт анимации!!!
                    }
                });

        //Подписка на изменение токена
        Intent intent = new Intent(this, MenuActivity.class); //переключение на другой активити
        MutableLiveData<String> liveData = AuthService.getLiveData();
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String userToken) {
                if (userToken.equals("Logout")){
                    return;
                }
                if (userToken.equals("errorLogin")){
                    //Вывести ошибочку логина на экран
                    errorTextLogin.setVisibility(View.VISIBLE);
                    gifImageView.setVisibility(View.INVISIBLE);
                    //Конец анимации
                    return;
                }
                try {
                    errorTextLogin.setVisibility(View.INVISIBLE);
                    userService.getUserData(userToken);
                    Log.d("auth", "login success");
                    gifImageView.setVisibility(View.INVISIBLE);//тут не видмой
                    //Конец анимации
                    startActivity(intent);
                } catch (Exception e){
                    e.printStackTrace();
                    Log.d("error", "Server userCheck problem"); //вывод текста об ошибке логина
                }
            }
        });
    }
}