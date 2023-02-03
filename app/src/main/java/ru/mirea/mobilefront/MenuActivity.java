package ru.mirea.mobilefront;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ru.mirea.mobilefront.adapter.BookTypeAdapter;
import ru.mirea.mobilefront.databinding.FragmentFullBookBinding;
import ru.mirea.mobilefront.adapter.MyFragmentAdapter;
import ru.mirea.mobilefront.dto.BookFull;
import ru.mirea.mobilefront.fragments.BasketFragment;
import ru.mirea.mobilefront.service.BasketService;
import ru.mirea.mobilefront.service.BookService;

public class MenuActivity extends FragmentActivity {

      private FragmentFullBookBinding fullBookBinding;
      private static BottomSheetBehavior<View> staticBottomSheetBehavior;
      private TabLayout tabLayout;
      private ViewPager2 viewPager2;
      private MyFragmentAdapter adapter;
      private AppCompatButton minus_btn;
      private AppCompatButton plus_btn;
      private EditText num_btn;
      private Button closeFullBookButton;
//    private static final int NUM_PAGES=5;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BasketService.getBasketBookList().postValue(new HashMap<BookFull, Integer>());
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
        FrameLayout layout = findViewById(R.id.bottom_sheet);
        layout.bringToFront();
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(layout);

        if (staticBottomSheetBehavior==null)
            staticBottomSheetBehavior = bottomSheetBehavior;

        bottomSheetLogic(bottomSheetBehavior, layout);

    }


    //Тут все связанное с логикой нижней панели.
    private ImageSlider imageSlider;
    private MaterialButton plusButton;
    private MaterialButton minusButton;
    private EditText countText;
    private TextView finalSumText;
    @RequiresApi(api = Build.VERSION_CODES.M)


    public void bottomSheetLogic(BottomSheetBehavior<View> bottomSheetBehavior, FrameLayout frameLayout){

        TextView textBookName = findViewById(R.id.text_book_name);
        TextView textArticul = findViewById(R.id.text_articul);
        TextView textAuthor = findViewById(R.id.text_author);
        TextView textPrice = findViewById(R.id.text_price);
        TextView textGenre = findViewById(R.id.text_genre);
        TextView textDescription = findViewById(R.id.text_description_book);
        plusButton = findViewById(R.id.change_count_plus);
        minusButton = findViewById(R.id.change_count_minus);
        countText = findViewById(R.id.edit_count_books);
        finalSumText = findViewById(R.id.final_summ_view);
        bottomSheetBehavior.setPeekHeight(0);
        imageSlider = findViewById(R.id.image_slider);
        ArrayList<SlideModel> imageList = new ArrayList<SlideModel>();

       /* closeButtonFullBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }); */
        plusButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                BookService.getCurrentChosenBook().getValue().getPrice();
                int count = Integer.parseInt(countText.getText().toString())+1;
                countText.setText(String.valueOf(count));
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                BookService.getCurrentChosenBook().getValue().getPrice();
                int count = Integer.parseInt(countText.getText().toString())-1;
                countText.setText(String.valueOf(count));
            }
        });

        BookService.getCurrentChosenBook().observe(this, new Observer<BookFull>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(BookFull bookFull) {
                textBookName.setText(bookFull.getBookName()); //Book name set
                imageList.clear();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                bookFull.getImageUrl()
                        .forEach(a -> imageList.add(new SlideModel(a, ScaleTypes.CENTER_INSIDE)));
                imageSlider.setImageList(imageList);

                //Description set
                if (BasketService.getBasketBookList().getValue().containsKey(bookFull)){
                    int count = BasketService.getBasketBookList().getValue().get(bookFull);
                    countText.setText(String.valueOf(count));
                    finalSumText.setText("Общая стоимость: "+ String.valueOf(BookService.getCurrentChosenBook().getValue().getPrice()*count)+" руб.");
                }
                textArticul.setText(bookFull.getArticul());
                textAuthor.setText(bookFull.getAuthor());
                textPrice.setText(String.valueOf(bookFull.getPrice()));
                textGenre.setText(bookFull.getGenre());
                textDescription.setText(bookFull.getDescription());
            }

        });
        ScrollView descriptionScrollView = findViewById(R.id.description_scroll_view);

        countText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int number = Integer.parseInt(s.toString());
                    if ((number < 0) || (number>100)) {
                        countText.setText("0");
                        finalSumText.setText("Товар пока не добавлен в корзину");

                    }
                    if ((number>0) && (number<=100)){
                        finalSumText.setText("Общая стоимость: "+ String.valueOf(BookService.getCurrentChosenBook().getValue().getPrice()*number)+" руб.");

                    }
                    if (number==0){
                        finalSumText.setText("Товар пока не добавлен в корзину");
                    }
                } catch (NumberFormatException e) {
                    countText.setText("0");
                    finalSumText.setText("Товар пока не добавлен в корзину");
                }

            }
        });


        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED){

                    if ((BasketService.getBasketBookList().getValue()
                            .containsKey(BookService.getCurrentChosenBook().getValue())) && (Integer.parseInt(countText.getText().toString())==0)){
                        HashMap<BookFull, Integer> basketData = BasketService.getBasketBookList().getValue();
                        basketData.remove(BookService.getCurrentChosenBook().getValue());
                        BasketService.getBasketBookList().postValue(basketData);
                    }

                    if (Integer.parseInt(countText.getText().toString())>0) {
                        HashMap<BookFull, Integer> basketData = BasketService.getBasketBookList().getValue();
                        basketData.put(BookService.getCurrentChosenBook().getValue(), Integer.parseInt(countText.getText().toString()));
                        BasketService.getBasketBookList().postValue(basketData);
                    }
                    //сбросить текст
                    countText.setText("0");
                    finalSumText.setText("Товар пока не добавлен в корзину");
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    public static BottomSheetBehavior<View> getStaticBottomSheetBehavior(){
        return staticBottomSheetBehavior;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int appHeight = getWindow().getDecorView().getHeight();
        ViewGroup.LayoutParams layoutParams = viewPager2.getLayoutParams();
        layoutParams.height = appHeight-2*tabLayout.getHeight(); // set height to fill screen
    }

}