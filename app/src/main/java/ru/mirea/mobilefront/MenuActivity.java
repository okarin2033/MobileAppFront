package ru.mirea.mobilefront;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ru.mirea.mobilefront.databinding.FragmentFullBookBinding;
import ru.mirea.mobilefront.design.MyFragmentAdapter;
import ru.mirea.mobilefront.dto.BookFull;
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
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void bottomSheetLogic(BottomSheetBehavior<View> bottomSheetBehavior, FrameLayout frameLayout){

        TextView textBookName = findViewById(R.id.text_book_name);
        TextView textArticul = findViewById(R.id.text_articul);
        TextView textAuthor = findViewById(R.id.text_author);
        TextView textPrice = findViewById(R.id.text_price);
        TextView textGenre = findViewById(R.id.text_genre);
        TextView textDescription = findViewById(R.id.text_description_book);
        bottomSheetBehavior.setPeekHeight(0);
        imageSlider = findViewById(R.id.image_slider);
        ArrayList<SlideModel> imageList = new ArrayList<SlideModel>();

       /* closeButtonFullBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }); */

        BookService.getCurrentChosenBook().observe(this, new Observer<BookFull>() {
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
                textArticul.setText(bookFull.getArticul());
                textAuthor.setText(bookFull.getAuthor());
                textPrice.setText(String.valueOf(bookFull.getPrice()));
                textGenre.setText(bookFull.getGenre());
                textDescription.setText(bookFull.getDescription());
            }

        });
        ScrollView descriptionScrollView = findViewById(R.id.description_scroll_view);


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