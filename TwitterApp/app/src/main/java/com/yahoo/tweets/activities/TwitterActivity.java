package com.yahoo.tweets.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.yahoo.tweets.R;
import com.yahoo.tweets.TwitterApp;
import com.yahoo.tweets.adapters.PostTweetDialog;
import com.yahoo.tweets.models.LoggedInUser;
import com.yahoo.tweets.utils.DialogCallBack;
import com.yahoo.tweets.utils.EndlessScrollListener;
import com.yahoo.tweets.utils.TwitterClient;
import com.yahoo.tweets.adapters.TweetAdapter;
import com.yahoo.tweets.models.TweetModel;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TwitterActivity extends ActionBarActivity implements DialogCallBack {

    private List<TweetModel> tweetList;
    private TweetAdapter twitterAdapter;
    private TwitterClient twitterClient;
    private ListView lvTweets;
    private SwipeRefreshLayout swipeContainer;
    private String minIdSoFar = "-1";
    // to be used in refresh
    private Long sinceId;
    private LoggedInUser loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        tweetList = new ArrayList<>();
        twitterAdapter = new TweetAdapter(this,tweetList);
        twitterClient = TwitterApp.getRestClient();

        ImageButton imageButton = (ImageButton) findViewById(R.id.ibPostTweet);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = fetchFragmentManager();
                //SettingDialogFragment settingDialogFragment = new SettingDialogFragment();
                PostTweetDialog tweetDialogFragment = PostTweetDialog.newInstance("Post Tweet");
                Bundle args = new Bundle();
                args.putLong("in_reply_to", 0);
                args.putString("text", "");
                args.putSerializable("user",loggedInUser);
                tweetDialogFragment.setArguments(args);
                tweetDialogFragment.show(fm, "Post Tweet");
            }
        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    populateTweetList(1);
                }catch(Exception e) {

                }
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        lvTweets = (ListView) findViewById(R.id.lvTweets);
        lvTweets.setAdapter(twitterAdapter);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTweetList(1);
            }
        });

        lvTweets.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
              //  Toast.makeText(TwitterActivity.this,"Item clicked at position " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TwitterActivity.this, TweetDetailActivity.class);
                intent.setAction("ItemDetail");
                intent.putExtra("Tweet",tweetList.get(position));
                startActivity(intent);
                return true;
            }
        });

        EditText etTweet = (EditText) findViewById(R.id.etMyDummyTweet);
        etTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = fetchFragmentManager();
                //SettingDialogFragment settingDialogFragment = new SettingDialogFragment();
                PostTweetDialog tweetDialogFragment = PostTweetDialog.newInstance("Post Tweet");
                Bundle args = new Bundle();
                args.putLong("in_reply_to", 0);
                args.putString("text", "");
                args.putSerializable("user",loggedInUser);
                tweetDialogFragment.setArguments(args);
                tweetDialogFragment.show(fm, "Post Tweet");
            }
        });

        loggedInUser = new LoggedInUser();
        fetchLoggedInUserDetails(loggedInUser);
        populateTweetList(1);
    }

    private void populateTweetList(long sinceId) {

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                int start = 0;
                if(!minIdSoFar.equals("-1")) {
                    start = 1;
                }
                for(;start<response.length();start++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(start);
                        TweetModel tweetModel = new TweetModel(jsonObject);
                        // Response is id ordered
                        minIdSoFar = tweetModel.getTweetId();
                        tweetList.add(tweetModel);
                    } catch(JSONException e) {

                    }
                }
                twitterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        };

        twitterClient.getTweetsList(jsonHttpResponseHandler,sinceId,minIdSoFar);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public FragmentManager fetchFragmentManager() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager;
    }

    public void onTweetPost() {
        minIdSoFar = "-1";
        tweetList.clear();
        twitterAdapter.notifyDataSetChanged();
        populateTweetList(1);
    }

    private void fetchLoggedInUserDetails(final LoggedInUser loggedInUser) {

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if(response.length() >= 1) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(0);
                        String userName = jsonObject.getJSONObject("user").getString("name");
                        String screenName = jsonObject.getJSONObject("user").getString("screen_name");
                        String profileURL = jsonObject.getJSONObject("user").getString("profile_image_url");
                        loggedInUser.setProfileURL(profileURL);
                        loggedInUser.setScreenName(screenName);
                        loggedInUser.setUserName(userName);
                    }
                    catch(JSONException e) {

                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        };

        new TwitterClient(this).getUserTimeline(jsonHttpResponseHandler);
    }

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

}
