package com.yahoo.tweets.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.yahoo.tweets.R;
import com.yahoo.tweets.adapters.PostTweetDialog;
import com.yahoo.tweets.adapters.TweetsPagerAdapter;
import com.yahoo.tweets.utils.DialogCallBack;
import com.yahoo.tweets.utils.TwitterRestClient;

public class TwitterActivity extends ActionBarActivity implements DialogCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        setUpActionBar();

        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        TwitterRestClient.saveLoggedInUserDetails(editor);

        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new TweetsPagerAdapter(getBaseContext(),getSupportFragmentManager()));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
    }

    private void setUpActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff60b6ff")));
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_menu_bar, null);
        ((TextView) mCustomView.findViewById(R.id.text)).setText("Home");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_profile) {
            Intent intent = new Intent(this,ProfileActivity.class);
            intent.putExtra("screen_name",getPreferences(MODE_PRIVATE).getString("twitterMyScreenName",""));
            startActivity(intent);
        }
        else if(id == R.id.action_search) {
        }
        else if(id == R.id.action_tweet) {
            onClick();
        }
        else if(id == R.id.action_message) {

        }
        //noinspection SimplifiableIfStatement
        else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        PostTweetDialog tweetDialogFragment = PostTweetDialog.newInstance("Post Tweet");
        Bundle args = new Bundle();
        args.putLong("in_reply_to", 0);
        args.putString("text", "");
        tweetDialogFragment.setArguments(args);
        tweetDialogFragment.show(fm, "Post Tweet");
    }

    public void onClick(View v) {
        onClick();
    }

    public void onTweetPost() {
        ((UserTimeLineFragment)new TweetsPagerAdapter(getBaseContext(),getSupportFragmentManager()).getRegisteredFragment(0)).refreshTweet();
    }
}
