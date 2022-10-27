package ru.mirea.mobilefront;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button testforanim;//переменная для id кнопки (для анимации)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testforanim=(Button)findViewById(R.id.switch_remember);//получаем id кнопочки
        testforanim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation An1 = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bounce);//Получаем анимацию
                testforanim.startAnimation(An1);//Запуск анимации кнопки
            }
        });



    }
}