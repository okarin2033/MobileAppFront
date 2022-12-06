package ru.mirea.mobilefront;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import ru.mirea.mobilefront.databinding.FragmentFullBookBinding;
import ru.mirea.mobilefront.design.MyFragmentAdapter;

public class MenuActivity extends FragmentActivity {

      private FragmentFullBookBinding fullBookBinding;

      private TabLayout tabLayout;
      private ViewPager2 viewPager2;
      private MyFragmentAdapter adapter;
      private AppCompatButton minus_btn;
      private AppCompatButton plus_btn;
      private EditText num_btn;
      private Button closeFullBookButton;
//    private static final int NUM_PAGES=5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullBookBinding = FragmentFullBookBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_menu);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager2=findViewById(R.id.full_book_frame);

        viewPager2.setUserInputEnabled(false); //отключение возможности скролить пальцем, оставляя только меню

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter=new MyFragmentAdapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(adapter);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition(), true); //false - отключает прокрутку (анимация)
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


    //Меню полной книги
        closeFullBookButton = fullBookBinding.closeButtonFullBook;
        closeFullBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Niggeer");
                fullBookBinding.fullBookContainer.setVisibility(View.GONE);
            }
        });
    }



}