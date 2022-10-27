package ru.mirea.mobilefront;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.graphics.Color;
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

                Animation Bounce = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fadein); //лучаем анимацию
                testforanim.startAnimation(Bounce);//Запуск анимации кнопки



                //changeViewColor(view);//вызов метода снизу
            }
        });

    }
    private void changeViewColor(View view) {
        // Load initial and final colors.
        final int initialColor = getResources().getColor(R.color.purple_200);
        final int finalColor = getResources().getColor(R.color.white);

        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Use animation position to blend colors.
                float position = animation.getAnimatedFraction();
                int blended = blendColors(initialColor, finalColor, position);

                // Apply blended color to the view.
                view.setBackgroundColor(blended);
            }
        });

        anim.setDuration(500).start();
    }

    private int blendColors(int from, int to, float ratio) {
        final float inverseRatio = 1f - ratio;

        final float r = Color.red(to) * ratio + Color.red(from) * inverseRatio;
        final float g = Color.green(to) * ratio + Color.green(from) * inverseRatio;
        final float b = Color.blue(to) * ratio + Color.blue(from) * inverseRatio;

        return Color.rgb((int) r, (int) g, (int) b);
    }
}