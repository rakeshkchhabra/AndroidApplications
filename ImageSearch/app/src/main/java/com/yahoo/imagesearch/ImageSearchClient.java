package com.yahoo.imagesearch;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakeshch on 1/31/15.
 */
public class ImageSearchClient {

    public static void fetchImages(final List<ImageItem> imageItems, SearchParam searchParams, final ImageAdapter imageAdapter) {

        String urlPattern = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q={0}&rsz=8&label={1}&start={2}&as_filetype={3}&as_sitesearch={4}&imgcolor={5}&imgsz={6}";
        String url = MessageFormat.format(urlPattern,searchParams.queryString,searchParams.label,searchParams.start, ImageSearchConstants.imageTypeFilters[searchParams.imageTypePosition], searchParams.siteFilter, ImageSearchConstants.colorFilters[searchParams.colorFilterPosition], ImageSearchConstants.imageSizes[searchParams.imageSizePosition]);
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        Log.d("tag",url);

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray results = response.getJSONObject("responseData").getJSONArray("results");
                    for(int i = 0; i < results.length(); i++) {
                        ImageItem imageItem = new ImageItem();
                        JSONObject result = results.getJSONObject(i);
                        imageItem.imageUrl = result.getString("unescapedUrl");
                        imageItem.tabWidth = Integer.parseInt(result.getString("tbWidth"));
                        imageItem.tabHeight = Integer.parseInt(result.getString("tbHeight"));
                        imageItem.height = Integer.parseInt(result.getString("height"));
                        imageItem.width = Integer.parseInt(result.getString("width"));
                        imageItem.tbImageUrl = result.getString("tbUrl");
                        imageItem.title = result.getString("title");
                        Log.i("dimension", imageItem.toString());
                        imageItems.add(imageItem);
                        imageAdapter.notifyDataSetChanged();
                        Log.i("Size now " , " " + imageItems.size());
                    }
                }
                catch (JSONException e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        };
        Log.d("Debug",url);
        asyncHttpClient.get(url,jsonHttpResponseHandler);
    }

}
