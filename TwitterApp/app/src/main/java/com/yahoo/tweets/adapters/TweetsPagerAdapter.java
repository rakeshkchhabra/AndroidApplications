package com.yahoo.tweets.adapters;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import com.yahoo.tweets.activities.UserMentionsFragment;
import com.yahoo.tweets.activities.UserTimeLineFragment;

public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter {

    final int PAGE_COUNT = 2;
    private String[] tabTitles = {"Home", "Mentions"};
    private Context context;
    public TweetsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new UserTimeLineFragment();
        } else if (position == 1) {
            return new UserMentionsFragment();
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
