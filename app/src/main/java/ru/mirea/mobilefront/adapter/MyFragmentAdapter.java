package ru.mirea.mobilefront.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ru.mirea.mobilefront.fragments.BasketFragment;
import ru.mirea.mobilefront.fragments.BookFragment;
import ru.mirea.mobilefront.fragments.MenuFragment;
import ru.mirea.mobilefront.fragments.ProfileFragment;
import ru.mirea.mobilefront.fragments.SearchFragment;

public class MyFragmentAdapter extends FragmentStateAdapter {

    public MyFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
                case 0:
                    return new BookFragment();
                case 1:
                    return new SearchFragment();
                case 2:
                    return new BasketFragment();
                case 3:
                    return new MenuFragment();
                case 4:
                    return new ProfileFragment();
                default:
                    return null;

            }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
