package com.example.hp.k_tech_task;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class LBPageAdapter extends FragmentStatePagerAdapter
{
    private static int NUM_ITEMS=3;
    private static String tag[]={"EASY","MEDIUM","HARD"};
    LeaderboardEasy lfe;
    LeaderboardMedium lfm;
    LeaderboardHard lfh;
    @Override
    public Fragment getItem(int position)
    {
        switch(position)
        {
            case 0:
                lfe = LeaderboardEasy.newInstance(tag[0]);
                return lfe;
            case 1:
                lfm=LeaderboardMedium.newInstance(tag[1]);
                return lfm;
            case 2:
                lfh=LeaderboardHard.newInstance(tag[2]);
                return lfh;
        }
        return null;
    }

    public LBPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
      return tag[position];
    }
}
