package ru.mirea.mobilefront.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;

import ru.mirea.mobilefront.MenuActivity;
import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.dto.BookFull;
import ru.mirea.mobilefront.service.BasketService;
import ru.mirea.mobilefront.service.BookService;

public class BottomSheetManager {


    private ImageSlider imageSlider;
    private MaterialButton plusButton;
    private MaterialButton minusButton;
    private EditText countText;
    private TextView finalSumText;
    private BottomSheetBehavior<View> staticBottomSheetBehavior;
    private FragmentActivity view;

    @RequiresApi(api = Build.VERSION_CODES.M)


    public BottomSheetManager(FragmentActivity view, BottomSheetBehavior<View> bottomSheetBehavior, FrameLayout frameLayout){
        this.view = view;
        staticBottomSheetBehavior = MenuActivity.getStaticBottomSheetBehavior();
        TextView textBookName = view.findViewById(R.id.text_book_name);
        TextView textArticul = view.findViewById(R.id.text_articul);
        TextView textAuthor = view.findViewById(R.id.text_author);
        TextView textPrice = view.findViewById(R.id.text_price);
        TextView textGenre = view.findViewById(R.id.text_genre);
        TextView textDescription = view.findViewById(R.id.text_description_book);
        plusButton = view.findViewById(R.id.change_count_plus);
        minusButton = view.findViewById(R.id.change_count_minus);
        countText = view.findViewById(R.id.edit_count_books);
        finalSumText = view.findViewById(R.id.final_summ_view);
        bottomSheetBehavior.setPeekHeight(0);
        imageSlider = view.findViewById(R.id.image_slider);
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

        BookService.getCurrentChosenBook().observe(view, new Observer<BookFull>() {
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
        ScrollView descriptionScrollView = view.findViewById(R.id.description_scroll_view);

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

    public void onBackPressed(ViewPager2 viewPager2) {
        if (staticBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            staticBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            int currentItem = viewPager2.getCurrentItem();
            if (currentItem == 0) {
                // If the user is currently on the first page of the ViewPager2, you can finish the activity.
                view.onBackPressed();
            } else {
                // If the user is not on the first page, you can navigate back to the first page.
                viewPager2.setCurrentItem(0, true);
            }
        }

    }
}

