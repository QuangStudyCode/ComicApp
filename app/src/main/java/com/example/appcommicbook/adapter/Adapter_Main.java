package com.example.appcommicbook.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appcommicbook.categoryFragment;
import com.example.appcommicbook.homeFragment;
import com.example.appcommicbook.searchFragment;
import com.example.appcommicbook.topcomicFragment;
import com.example.appcommicbook.userFragment;

public class Adapter_Main extends FragmentStateAdapter {

    public Adapter_Main(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new homeFragment();
            case 1:
                return new topcomicFragment();
            case 2:
                return new categoryFragment();
            case 3:
                return new searchFragment();
            case 4:
                return new userFragment();
            default:
                return new homeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
