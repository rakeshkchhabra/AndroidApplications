package com.yahoo.tweets.utils;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.tweets.TwitterApp;
import com.yahoo.tweets.adapters.TweetAdapter;
import com.yahoo.tweets.adapters.UserAdapter;
import com.yahoo.tweets.models.ListItem;
import com.yahoo.tweets.models.ProfileModel;
import com.yahoo.tweets.models.TweetHeader;
import com.yahoo.tweets.models.TweetModel;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by rakeshch on 2/14/15.
 */
public class TwitterRestClient {

    private static final TwitterClient twitterClient = TwitterApp.getRestClient();

    public static void saveLoggedInUserDetails(final SharedPreferences.Editor editor) {

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if(response.length() >= 1) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(0);
                        String userName = jsonObject.getJSONObject("user").getString("name");
                        String screenName = jsonObject.getJSONObject("user").getString("screen_name");
                        String profileURL = jsonObject.getJSONObject("user").getString("profile_image_url");
                        editor.putString("twitterMyUserName", userName);
                        editor.putString("twitterMyScreenName",screenName );
                        editor.putString("twitterMyProfileURL", profileURL);
                        editor.commit();
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

        twitterClient.getUserTimeline(jsonHttpResponseHandler,"");
    }


    public static void populateTweetList(final PaginationParams paginationParams, final List<ListItem> tweetList, final TweetAdapter tweetAdapter) {

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                int start = 0;
                if(paginationParams.getMinIdSoFar() != -1) {
                    start = 1;
                }
                for(;start<response.length();start++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(start);
                        TweetModel tweetModel = new TweetModel(jsonObject);
                        // Response is id ordered
                        paginationParams.setMinIdSoFar(Long.parseLong(tweetModel.getTweetId()));
                        tweetList.add(tweetModel);
                    } catch(JSONException e) {

                    }
                }
                tweetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        };

        twitterClient.getTweetsList(jsonHttpResponseHandler,paginationParams.getSinceId(),paginationParams.getMinIdSoFar());
    }

    public static void populateUserMention(final PaginationParams paginationParams, final List<ListItem> tweetList, final TweetAdapter tweetAdapter) {

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                int start = 0;
                if(paginationParams.getMinIdSoFar() != -1) {
                    start = 1;
                }
                for(;start<response.length();start++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(start);
                        TweetModel tweetModel = new TweetModel(jsonObject);
                        // Response is id ordered
                        paginationParams.setMinIdSoFar(Long.parseLong(tweetModel.getTweetId()));
                        tweetList.add(tweetModel);
                    } catch(JSONException e) {

                    }
                }
                tweetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        };

        twitterClient.getUserMentions(jsonHttpResponseHandler,paginationParams.getSinceId(),paginationParams.getMinIdSoFar());
    }

    public static void populateTweetListFromUserTimeline(final PaginationParams paginationParams, final List<ListItem> tweetList, final TweetAdapter tweetAdapter, final String screenName) {

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                int start = 0;
                if(paginationParams.getMinIdSoFar() != -1) {
                    start = 1;
                }
                Log.i("DEBUG", "here?2??");

                for(;start<response.length();start++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(start);
                        TweetModel tweetModel = new TweetModel(jsonObject);
                        // Response is id ordered
                        paginationParams.setMinIdSoFar(Long.parseLong(tweetModel.getTweetId()));
                        tweetList.add(tweetModel);

                    } catch(JSONException e) {

                    }
                }
                Log.i("DEBUG", "here?1???");

                tweetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        };

        twitterClient.getUserTimeline(jsonHttpResponseHandler,screenName);
    }

    public static void fetchUserProfileBanner(final ProfileModel profileModel, final String screenName, final TweetAdapter tweetAdapter) {

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    profileModel.setBannerImageURL(response.getJSONObject("sizes").getJSONObject("mobile").getString("url"));
                    profileModel.setProfileWidth(response.getJSONObject("sizes").getJSONObject("mobile").getInt("w"));
                    profileModel.setProfileHeight(response.getJSONObject("sizes").getJSONObject("mobile").getInt("h"));
                    tweetAdapter.notifyDataSetChanged();
                } catch(JSONException e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        };

        twitterClient.getUserProfileBanner(jsonHttpResponseHandler,screenName);
    }

    public static void fetchUserProfileAndTimeLine(final List<ListItem> listItem, final TweetAdapter tweetAdapter, final String screenName, final PaginationParams paginationParams) {

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                ProfileModel profileModel = new ProfileModel(response);
                TweetHeader tweetHeader = new TweetHeader(profileModel);
                listItem.add(tweetHeader);
                Log.i("DEBUG", "here????");
                //fetchUserProfileBanner(profileModel,screenName,tweetAdapter);
                tweetAdapter.notifyDataSetChanged();
                populateTweetListFromUserTimeline(paginationParams, listItem, tweetAdapter, screenName);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        };

        twitterClient.getUserProfile(jsonHttpResponseHandler, screenName);
    }


    public static void fetchUserFollowers(final List<ProfileModel> profileModels, final UserAdapter userAdapter, final String screenName) {

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("users");
                    for(int i = 0;i < jsonArray.length(); i++) {
                        ProfileModel profileModel = new ProfileModel(jsonArray.getJSONObject(i));
                        profileModels.add(profileModel);
                    }
                    userAdapter.notifyDataSetChanged();
                } catch(JSONException e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        };

        twitterClient.getUserFollowers(jsonHttpResponseHandler, screenName);
    }

    public static void fetchUserFriends(final List<ProfileModel> profileModels, final UserAdapter userAdapter, final String screenName) {

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("users");
                    for(int i = 0;i < jsonArray.length(); i++) {
                        ProfileModel profileModel = new ProfileModel(jsonArray.getJSONObject(i));
                        profileModels.add(profileModel);
                    }
                    userAdapter.notifyDataSetChanged();
                } catch(JSONException e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        };

        twitterClient.getUserFriends(jsonHttpResponseHandler,screenName);
    }

}
