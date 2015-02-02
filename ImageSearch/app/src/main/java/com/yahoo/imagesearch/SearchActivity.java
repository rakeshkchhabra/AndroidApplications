package com.yahoo.imagesearch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class SearchActivity extends ActionBarActivity {

    private List<ImageItem> imageItems;
    private ImageAdapter imageAdapter;
    private SearchParam lastSearchParam = new SearchParam();
    private int label = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imageItems = new ArrayList<>();
        imageAdapter = new ImageAdapter(this,imageItems);
        GridView gridView = (GridView) findViewById(R.id.gvImages);
        gridView.setAdapter(imageAdapter);

        gridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
    }

    private void customLoadMoreDataFromApi(int offset) {
        SearchParam searchParam = new SearchParam();
        searchParam.queryString = lastSearchParam.queryString;
        searchParam.imageSizePosition = lastSearchParam.imageSizePosition;
        searchParam.colorFilterPosition = lastSearchParam.colorFilterPosition;
        searchParam.imageTypePosition = lastSearchParam.imageTypePosition;
        searchParam.siteFilter = lastSearchParam.siteFilter;
        searchParam.label = offset;
        searchParam.start = (offset-1)*8;
        ImageSearchClient.fetchImages(imageItems, searchParam);

        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.searchAction);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                imageItems.clear();
                imageAdapter.notifyDataSetChanged();
                lastSearchParam.queryString = query;
                label = 1;

                SearchParam searchParam = new SearchParam();
                searchParam.queryString = query;
                searchParam.label = label;
                searchParam.start = (label-1) * 8;
                ImageSearchClient.fetchImages(imageItems, searchParam);

                imageAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.miSetting) {
            android.support.v4.app.FragmentManager fm = fetchFragmentManager();
            SettingDialogFragment settingDialogFragment = SettingDialogFragment.newInstance("Settings");
            Bundle args = new Bundle();
            args.putSerializable("Settings", lastSearchParam);
            settingDialogFragment.setArguments(args);
            settingDialogFragment.show(fm, "settings");
        }
        return super.onOptionsItemSelected(item);
    }

    public FragmentManager fetchFragmentManager() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == "200") {
                //Toast.makeText(getBaseContext(),"Received the settings back",Toast.LENGTH_SHORT).show();
                SearchParam searchParam = (SearchParam) intent.getSerializableExtra("settings");
                Log.d("result",searchParam.colorFilterPosition + " " + searchParam.imageSizePosition + " " + searchParam.imageTypePosition + " " + searchParam.siteFilter);
                searchParam.label = 1;
                searchParam.start = 0;
                searchParam.queryString = lastSearchParam.queryString;
                lastSearchParam = searchParam;
                imageItems.clear();
                imageAdapter.notifyDataSetChanged();
                ImageSearchClient.fetchImages(imageItems, searchParam);
                imageAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter("200"));
    }
}
