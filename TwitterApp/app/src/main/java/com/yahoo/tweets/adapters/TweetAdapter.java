package com.yahoo.tweets.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.tweets.R;
import com.yahoo.tweets.activities.TwitterActivity;
import com.yahoo.tweets.models.ListItem;

import java.util.List;

/**
 * Created by rakeshch on 2/7/15.
 */
public class TweetAdapter extends ArrayAdapter<ListItem> {

    private LayoutInflater mInflater;
    private Context context;

    public TweetAdapter(Context context, List<ListItem> tweetModels) {
        super(context,R.layout.tweet, tweetModels);
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(mInflater,convertView, parent, context);
    }
}
