package com.yahoo.tweets.activities;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yahoo.tweets.R;
import com.yahoo.tweets.adapters.TweetAdapter;
import com.yahoo.tweets.models.ListItem;
import com.yahoo.tweets.utils.PaginationParams;
import com.yahoo.tweets.utils.TwitterRestClient;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends ActionBarActivity {

    private List<ListItem> tweetItems;
    private TweetAdapter twitterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpActionBar();
        tweetItems = new ArrayList<>();
        PaginationParams paginationParams = new PaginationParams(0,1);
        String screenName = getIntent().getStringExtra("screen_name");

        twitterAdapter = new TweetAdapter(this,tweetItems);
        ListView lvProfile = (ListView) findViewById(R.id.listView);
        lvProfile.setAdapter(twitterAdapter);
        TwitterRestClient.fetchUserProfileAndTimeLine(tweetItems, twitterAdapter, screenName, paginationParams);
    }

    private void setUpActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff60b6ff")));
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_menu_bar, null);
        ((TextView) mCustomView.findViewById(R.id.text)).setText("Profile");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
