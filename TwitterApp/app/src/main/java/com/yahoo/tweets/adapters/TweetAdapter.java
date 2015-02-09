package com.yahoo.tweets.adapters;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
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
import com.yahoo.tweets.models.TweetModel;

import java.util.List;

/**
 * Created by rakeshch on 2/7/15.
 */
public class TweetAdapter extends ArrayAdapter<TweetModel> {

    public TweetAdapter(Context context, List<TweetModel> tweetModels) {
        super(context,R.layout.tweet, tweetModels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final TweetModel tweetModel = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet, parent, false);
        }
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.llretweetlayout);

        if(tweetModel.getReTwittedBy()!=null && !tweetModel.getReTwittedBy().isEmpty()) {
            TextView reTwittedBy = (TextView) convertView.findViewById(R.id.tvReweetedBy);
            reTwittedBy.setText(tweetModel.getReTwittedBy() + " retweeted");
            reTwittedBy.setVisibility(View.VISIBLE);

            ImageView ivRetweetedBy = (ImageView) convertView.findViewById(R.id.ivRetweetedBy);
            ivRetweetedBy.setVisibility(View.VISIBLE);

            linearLayout.getLayoutParams().height = WindowManager.LayoutParams.WRAP_CONTENT;;
            linearLayout.getLayoutParams().width = WindowManager.LayoutParams.WRAP_CONTENT;;
        }
        else {
            ImageView ivRetweetedBy = (ImageView) convertView.findViewById(R.id.ivRetweetedBy);
            ivRetweetedBy.setVisibility(View.INVISIBLE);

            TextView reTwittedBy = (TextView) convertView.findViewById(R.id.tvReweetedBy);
            reTwittedBy.setVisibility(View.INVISIBLE);

            linearLayout.getLayoutParams().height = 0;
            linearLayout.getLayoutParams().width = 0;
        }
        TextView userName = (TextView) convertView.findViewById(R.id.tvUserName);
        userName.setText(tweetModel.getUserName());

        final TextView twitterHandle = (TextView) convertView.findViewById(R.id.tvTwitterHandle);
        twitterHandle.setText("@" + tweetModel.getTwitterHandle());

        TextView description = (TextView) convertView.findViewById(R.id.tvDescription);
        description.setText(tweetModel.getTweet());

        ImageView ivMedia = (ImageView) convertView.findViewById(R.id.ivTweetMedia);
        if(tweetModel.getMediaURL()!=null && !tweetModel.getMediaURL().isEmpty()) {
            ivMedia.setImageResource(0);
            ivMedia.getLayoutParams().height = Integer.parseInt(tweetModel.getMediaHeight());
            ivMedia.getLayoutParams().width = Integer.parseInt(tweetModel.getMediaWidth());
            Picasso.with(getContext()).load(tweetModel.getMediaURL()).into(ivMedia);
        }
        else {
            ivMedia.setImageResource(0);
            ivMedia.getLayoutParams().height = 0;
            ivMedia.getLayoutParams().width = 0;
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivUserPhoto);
        imageView.setImageResource(0);
        Picasso.with(getContext()).load(tweetModel.getUserImageURL()).into(imageView);

        TextView favoriteCount = (TextView) convertView.findViewById(R.id.tvFavoriteCount);
        favoriteCount.setText(tweetModel.getFavoriteCount());

        TextView reTweetCount = (TextView) convertView.findViewById(R.id.tvRetweetCount);
        reTweetCount.setText(tweetModel.getRetweetCount());

        TextView timeDiff = (TextView) convertView.findViewById(R.id.tvTimeDiff);

        long time = Long.parseLong(tweetModel.getTweetTime())/1000;
        long now = System.currentTimeMillis()/1000;
        if(now-time < 3600) {
            timeDiff.setText( (now-time)/60 + "m ");
        }
        else if(now-time < 86400) {
            timeDiff.setText( (now-time)/3600 + "h ");
        }
        else {
            timeDiff.setText( (now-time)/86400 + "d ");
        }

        ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.ibReply);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = ((TwitterActivity)getContext()).fetchFragmentManager();
                DialogFragment replyDialogFragment = PostTweetDialog.newInstance("Reply Tweet");
                Bundle args = new Bundle();
                args.putLong("in_reply_to", Long.parseLong(tweetModel.getTweetId()));
                String text = "@" + tweetModel.getTwitterHandle() + " ";
                if(tweetModel.getReTwittedByHandle()!=null && !tweetModel.getReTwittedByHandle().isEmpty()) {
                    text += "@" + tweetModel.getReTwittedByHandle() + " ";
                }
                args.putString("text", text);
                args.putSerializable("user",((TwitterActivity) getContext()).getLoggedInUser());
                replyDialogFragment.setArguments(args);
                replyDialogFragment.show(fm, "Reply Tweet");
            }
        });
        return convertView;
    }
}
