package com.yahoo.tweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yahoo.tweets.R;
import com.yahoo.tweets.TwitterApp;
import com.yahoo.tweets.adapters.TweetAdapter;
import com.yahoo.tweets.models.ListItem;
import com.yahoo.tweets.utils.EndlessScrollListener;
import com.yahoo.tweets.utils.PaginationParams;
import com.yahoo.tweets.utils.TwitterClient;
import com.yahoo.tweets.utils.TwitterRestClient;

import java.util.ArrayList;
import java.util.List;


public class UserTimeLineFragment extends Fragment {

    private List<ListItem> tweetList;
    private TweetAdapter twitterAdapter;
    private TwitterClient twitterClient;
    private ListView lvTweets;
    private SwipeRefreshLayout swipeContainer;
    private PaginationParams paginationParams = new PaginationParams(1,-1);

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_user_timeline, container, false);

        tweetList = new ArrayList<>();
        twitterAdapter = new TweetAdapter(this.getActivity().getBaseContext(),tweetList);
        twitterClient = TwitterApp.getRestClient();

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.scUserTimeline);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    TwitterRestClient.populateTweetList(paginationParams, tweetList, twitterAdapter);
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


        lvTweets = (ListView) view.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(twitterAdapter);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                TwitterRestClient.populateTweetList(paginationParams, tweetList, twitterAdapter);
            }
        });
        lvTweets.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TweetDetailActivity.class);
                intent.setAction("ItemDetail");
                intent.putExtra("Tweet",tweetList.get(position));
                startActivity(intent);
                return true;
            }
        });
        TwitterRestClient.populateTweetList(paginationParams, tweetList, twitterAdapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize data which are not view related
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // After activity is done loading
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void refreshTweet() {

    }

    public void retweet() {

    }

    public void favorite() {

    }

    public void unFavorite() {

    }
}

