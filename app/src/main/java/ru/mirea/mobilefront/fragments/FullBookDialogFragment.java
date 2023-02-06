package ru.mirea.mobilefront.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;

import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.dto.BookFull;
import ru.mirea.mobilefront.service.BasketService;
import ru.mirea.mobilefront.service.BookService;

public class FullBookDialogFragment extends BottomSheetDialogFragment {

    private ImageSlider imageSlider;
    private MaterialButton plusButton;
    private MaterialButton minusButton;
    private EditText countText;
    private TextView finalSumText;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private FragmentActivity view;
    private Dialog dialog;


    public static FullBookDialogFragment newInstance() {
        return new FullBookDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_book, container, false);
        dialog = getDialog();
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        BottomSheetBehavior behavior = dialog.getBehavior();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
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
                int count = Integer.parseInt(countText.getText().toString()) + 1;
                countText.setText(String.valueOf(count));
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                BookService.getCurrentChosenBook().getValue().getPrice();
                int count = Integer.parseInt(countText.getText().toString()) - 1;
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
                bookFull.getImageUrl()
                        .forEach(a -> imageList.add(new SlideModel(a, ScaleTypes.CENTER_INSIDE)));
                imageSlider.setImageList(imageList);

                //Description set
                if (BasketService.getBasketBookList().getValue().containsKey(bookFull)) {
                    int count = BasketService.getBasketBookList().getValue().get(bookFull);
                    countText.setText(String.valueOf(count));
                    finalSumText.setText("Общая стоимость: " + String.valueOf(BookService.getCurrentChosenBook().getValue().getPrice() * count) + " руб.");
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
                    if ((number < 0) || (number > 100)) {
                        countText.setText("0");
                        finalSumText.setText("Товар пока не добавлен в корзину");

                    }
                    if ((number > 0) && (number <= 100)) {
                        finalSumText.setText("Общая стоимость: " + String.valueOf(BookService.getCurrentChosenBook().getValue().getPrice() * number) + " руб.");

                    }
                    if (number == 0) {
                        finalSumText.setText("Товар пока не добавлен в корзину");
                    }
                } catch (NumberFormatException e) {
                    countText.setText("0");
                    finalSumText.setText("Товар пока не добавлен в корзину");
                }

            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

        System.out.println("Dissmissed");
        if ((BasketService.getBasketBookList().getValue()
                .containsKey(BookService.getCurrentChosenBook().getValue())) && (Integer.parseInt(countText.getText().toString()) == 0)) {
            HashMap<BookFull, Integer> basketData = BasketService.getBasketBookList().getValue();
            basketData.remove(BookService.getCurrentChosenBook().getValue());
            BasketService.getBasketBookList().postValue(basketData);
        }

        if (Integer.parseInt(countText.getText().toString()) > 0) {
            HashMap<BookFull, Integer> basketData = BasketService.getBasketBookList().getValue();
            basketData.put(BookService.getCurrentChosenBook().getValue(), Integer.parseInt(countText.getText().toString()));
            BasketService.getBasketBookList().postValue(basketData);
        }
        //сбросить текст
        countText.setText("0");
        finalSumText.setText("Товар пока не добавлен в корзину");
    }
        });


    }



}

