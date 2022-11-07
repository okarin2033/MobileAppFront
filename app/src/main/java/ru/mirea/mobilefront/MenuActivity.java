package ru.mirea.mobilefront;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import ru.mirea.mobilefront.fragments.basket_fragment;
import ru.mirea.mobilefront.fragments.book_fragment;
import ru.mirea.mobilefront.fragments.menu_fragment;
import ru.mirea.mobilefront.fragments.profile_fragment;
import ru.mirea.mobilefront.fragments.search_fragment;

public class MenuActivity extends FragmentActivity {

        private static final int NUM_PAGES=5;
        private ViewPager2 viewPager2;
        private FragmentStateAdapter pagerAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        viewPager2=findViewById(R.id.pager);
        pagerAdapter=new ScreenSlidePageAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
    }
    private class ScreenSlidePageAdapter extends FragmentStateAdapter {
        public ScreenSlidePageAdapter(MenuActivity mainActivity){
        super(mainActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new book_fragment();
                case 1:
                    return new search_fragment();
                case 2:
                    return new basket_fragment();
                case 3:
                    return new menu_fragment();
                case 4:
                    return new profile_fragment();
                default:
                    return null;

            }

        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    private class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE=0.85f;
        private static final float MIN_ALPHA=0.5f;
        @Override
        public void transformPage(@NonNull View page, float position) {
            int pageWidth=page.getWidth();
            int pageHeight=page.getHeight();
            if (position<-1){
                page.setAlpha(0f);
            }
            else if (position<=1){
                float scaleFactor=Math.max(MIN_SCALE,1-Math.abs(position));
                float vertMargin=pageHeight*(1-scaleFactor)/2;
                float horzMargin=pageWidth*(1-scaleFactor)/2;

                if (position<0){
                    page.setTranslationX(horzMargin-vertMargin/2);
                }else{
                    page.setTranslationX(-horzMargin+vertMargin/2);
                }
                page.setScaleX(scaleFactor);
                page.setScaleX(scaleFactor);
                page.setAlpha(MIN_ALPHA+(scaleFactor-MIN_SCALE)/(1-MIN_SCALE)*(1-MIN_SCALE));
            }
            else{
                page.setAlpha(0f);
            }
        }

    }
}