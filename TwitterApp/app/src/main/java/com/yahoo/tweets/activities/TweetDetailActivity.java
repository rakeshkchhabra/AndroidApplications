package com.yahoo.tweets.activities;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.tweets.R;
import com.yahoo.tweets.adapters.PostTweetDialog;
import com.yahoo.tweets.models.TweetModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TweetDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);

        final TweetModel tweetModel = (TweetModel) getIntent().getSerializableExtra("Tweet");
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llretweetlayout);

        if(tweetModel.getReTwittedBy()!=null && !tweetModel.getReTwittedBy().isEmpty()) {
            TextView reTwittedBy = (TextView) findViewById(R.id.tvReweetedBy);
            reTwittedBy.setText(tweetModel.getReTwittedBy() + " retweeted");
            reTwittedBy.setVisibility(View.VISIBLE);

            ImageView ivRetweetedBy = (ImageView) findViewById(R.id.ivRetweetedBy);
            ivRetweetedBy.setVisibility(View.VISIBLE);

            linearLayout.getLayoutParams().height = WindowManager.LayoutParams.WRAP_CONTENT;;
            linearLayout.getLayoutParams().width = WindowManager.LayoutParams.WRAP_CONTENT;;
        }
        else {
            ImageView ivRetweetedBy = (ImageView) findViewById(R.id.ivRetweetedBy);
            ivRetweetedBy.setVisibility(View.INVISIBLE);

            TextView reTwittedBy = (TextView) findViewById(R.id.tvReweetedBy);
            reTwittedBy.setVisibility(View.INVISIBLE);

            linearLayout.getLayoutParams().height = 0;
            linearLayout.getLayoutParams().width = 0;
        }
        TextView userName = (TextView) findViewById(R.id.tvUserName);
        userName.setText(tweetModel.getUserName());

        final TextView twitterHandle = (TextView) findViewById(R.id.tvTwitterHandle);
        twitterHandle.setText("@" + tweetModel.getTwitterHandle());

        TextView description = (TextView) findViewById(R.id.tvDescription);
        description.setText(tweetModel.getTweet());

        ImageView ivMedia = (ImageView) findViewById(R.id.ivTweetMedia);
        if(tweetModel.getMediaURL()!=null && !tweetModel.getMediaURL().isEmpty()) {
            ivMedia.setImageResource(0);
            ivMedia.getLayoutParams().height = Integer.parseInt(tweetModel.getMediaHeight());
            ivMedia.getLayoutParams().width = Integer.parseInt(tweetModel.getMediaWidth());
            Picasso.with(this).load(tweetModel.getMediaURL()).into(ivMedia);
        }
        else {
            ivMedia.setImageResource(0);
            ivMedia.getLayoutParams().height = 0;
            ivMedia.getLayoutParams().width = 0;
        }

        ImageView imageView = (ImageView) findViewById(R.id.ivUserPhoto);
        imageView.setImageResource(0);
        Picasso.with(this).load(tweetModel.getUserImageURL()).into(imageView);

        TextView favoriteCount = (TextView) findViewById(R.id.tvFavoriteCount);
        favoriteCount.setText(tweetModel.getFavoriteCount());

        TextView reTweetCount = (TextView) findViewById(R.id.tvRetweetCount);
        reTweetCount.setText(tweetModel.getRetweetCount());

        TextView timeDiff = (TextView) findViewById(R.id.tvTimeDiff);
        Date d = new Date(tweetModel.getTweetCreatedAt());
        timeDiff.setText(DateFormat.getDateTimeInstance().format(d));

        ImageButton imageButton = (ImageButton) findViewById(R.id.ibReply);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = fetchFragmentManager();
                DialogFragment replyDialogFragment = PostTweetDialog.newInstance("Reply Tweet");
                Bundle args = new Bundle();
                args.putLong("in_reply_to", Long.parseLong(tweetModel.getTweetId()));
                String text = "@" + tweetModel.getTwitterHandle() + " ";
                if(tweetModel.getReTwittedByHandle()!=null && !tweetModel.getReTwittedByHandle().isEmpty()) {
                    text += "@" + tweetModel.getReTwittedByHandle() + " ";
                }
                args.putString("text", text);
                replyDialogFragment.setArguments(args);
                replyDialogFragment.show(fm, "Reply Tweet");
            }
        });

        ImageButton ibBack = (ImageButton) findViewById(R.id.tweetSymbol);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TweetDetailActivity.this,TwitterActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweet_detail, menu);
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
}
