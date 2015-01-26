package com.example.rakeshch.instagrapmpopular;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static final String CLIENTID = "ae74515d68dd4f89a0c8bc68f8564df7";
    private List<PhotoModel> photoModelList = null;
    private PhotoAdapater photoAdapater;
    private SwipeRefreshLayout swipeContainer;
    private AsyncHttpClient client;
    private String instagramUrl;
    private JsonHttpResponseHandler jsonHttpResponseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    client.get(instagramUrl,jsonHttpResponseHandler);
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

        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {

        photoModelList = new ArrayList<>();
        photoAdapater = new PhotoAdapater(this,photoModelList);
        final ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(photoAdapater);
        instagramUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENTID;
        client = new AsyncHttpClient();
        jsonHttpResponseHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //url,height, username,caption
                try {
                    photoModelList.clear();
                    JSONArray photoJson = response.getJSONArray("data");
                    for (int i = 0; i < photoJson.length(); i++) {
                        PhotoModel photoModel = new PhotoModel();
                        JSONObject jsonObject = photoJson.getJSONObject(i);
                        photoModel.userName = jsonObject.getJSONObject("user").getString("username");
                        photoModel.userPhotoUrl = jsonObject.getJSONObject("user").getString("profile_picture");
                        if (jsonObject.getJSONObject("caption") != null) {
                            photoModel.caption = jsonObject.getJSONObject("caption").getString("text");
                        }
                        photoModel.imageUrl = jsonObject.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photoModel.imgHeight = jsonObject.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photoModel.likesCount = jsonObject.getJSONObject("likes").getInt("count");
                        photoModel.commentCount = jsonObject.getJSONObject("comments").getInt("count");

                        JSONArray commentArray = jsonObject.getJSONObject("comments").getJSONArray("data");
                        if(commentArray.length() > 0) {
                            photoModel.photoComment1 = new PhotoComment(commentArray.getJSONObject(0).getJSONObject("from").getString("username"),commentArray.getJSONObject(0).getJSONObject("from").getString("profile_picture"),commentArray.getJSONObject(0).getString("text"));
                        }
                        if(commentArray.length() > 1) {
                            photoModel.photoComment2 = new PhotoComment(commentArray.getJSONObject(1).getJSONObject("from").getString("username"),commentArray.getJSONObject(1).getJSONObject("from").getString("profile_picture"),commentArray.getJSONObject(1).getString("text"));
                        }
                        photoModelList.add(photoModel);
                    }
                    photoAdapater.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        client.get(instagramUrl,jsonHttpResponseHandler);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
