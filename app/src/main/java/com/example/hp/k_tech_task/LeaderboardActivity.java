package com.example.hp.k_tech_task;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LeaderboardActivity extends AppCompatActivity implements LeaderboardEasy.OnFragmentInteractionListener,TabLayout.OnTabSelectedListener ,LeaderboardMedium.OnFragmentInteractionListener,LeaderboardHard.OnFragmentInteractionListener{

    TabLayout tabLayout;
    ViewPager lpager;
    FragmentStatePagerAdapter lpageadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        lpager=(ViewPager)findViewById(R.id.lpager);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(lpager);
        lpageadapter=new LBPageAdapter(getSupportFragmentManager());
        lpager.setAdapter(lpageadapter);

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        lpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
