package com.yahoo.tweets.models;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.squareup.picasso.Picasso;
import com.yahoo.tweets.R;
import com.yahoo.tweets.activities.ProfileActivity;
import com.yahoo.tweets.activities.TwitterActivity;
import com.yahoo.tweets.adapters.PostTweetDialog;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the ActiveAndroid wiki for more details:
 * https://github.com/pardom/ActiveAndroid/wiki/Creating-your-database-model
 * 
 */
@Table(name = "items")
public class TweetModel extends Model implements ListItem, Serializable {
	// Define table fields
    @Column(name = "tweetId")
    private String tweetId;
	@Column(name = "name")
	private String userName;
    @Column(name = "handle")
    private String twitterHandle;
    @Column(name = "userImageURL")
    private String userImageURL;
    @Column(name = "tweet")
    private String tweet;
    @Column(name = "tweetTime")
    private String tweetTime;
    @Column(name = "favoriteCount")
    private String favoriteCount;
    @Column(name = "retweetCount")
    private String retweetCount;
    @Column(name = "retwittedBy")
    private String reTwittedBy;
    @Column(name = "retwittedByHandle")
    private String reTwittedByHandle;
    @Column(name = "mediaURL")
    private String mediaURL;
    @Column(name = "mediaHeight")
    private String mediaHeight;
    @Column(name = "mediaWidth")
    private String mediaWidth;
    @Column(name = "tweetCreatedAt")
    private String tweetCreatedAt;

	public TweetModel() {
		super();
	}

    @Override
    public String toString() {
        return "TweetModel{" +
                "tweetId='" + tweetId + '\'' +
                ", userName='" + userName + '\'' +
                ", twitterHandle='" + twitterHandle + '\'' +
                ", userImageURL='" + userImageURL + '\'' +
                ", tweet='" + tweet + '\'' +
                ", tweetTime='" + tweetTime + '\'' +
                ", favoriteCount='" + favoriteCount + '\'' +
                ", retweetCount='" + retweetCount + '\'' +
                ", reTwittedBy='" + reTwittedBy + '\'' +
                ", reTwittedByHandle='" + reTwittedByHandle + '\'' +
                ", mediaURL='" + mediaURL + '\'' +
                ", mediaHeight='" + mediaHeight + '\'' +
                ", mediaWidth='" + mediaWidth + '\'' +
                '}';
    }

    // Parse model from JSON
	public TweetModel(JSONObject object){
		super();

		try {
            JSONObject jsonObject;
            if(object.has("retweeted_status")) {
                this.reTwittedBy = object.getJSONObject("user").getString("name");
                this.reTwittedByHandle = object.getJSONObject("user").getString("screen_name");
                jsonObject = object.getJSONObject("retweeted_status");
            }
            else {
                jsonObject = object;
            }
            this.tweetId = jsonObject.getString("id_str");
            this.userName = jsonObject.getJSONObject("user").getString("name");
            this.twitterHandle = jsonObject.getJSONObject("user").getString("screen_name");
            this.userImageURL = jsonObject.getJSONObject("user").getString("profile_image_url");
            this.favoriteCount = String.valueOf(jsonObject.getInt("favorite_count"));
            this.retweetCount = String.valueOf(jsonObject.getInt("retweet_count"));
            this.tweet = jsonObject.getString("text");
            this.tweetCreatedAt = jsonObject.getString("created_at");
	        this.tweetTime = String.valueOf(Date.parse(jsonObject.getString("created_at")));

            if(jsonObject.has("entities") && ((JSONObject)jsonObject.get("entities")).has("media")) {
                JSONArray medias = ((JSONObject) jsonObject.get("entities")).getJSONArray("media");
                if(medias.length() >= 0) {
                    JSONObject media = (JSONObject) medias.get(0);
                    if (media.getString("type").equals("photo")) {
                        this.mediaURL = media.getString("media_url");
                        this.mediaHeight = String.valueOf(media.getJSONObject("sizes").getJSONObject("medium").getInt("h"));
                        this.mediaWidth = String.valueOf(media.getJSONObject("sizes").getJSONObject("medium").getInt("w"));
                    }
                }
            }


	    } catch (JSONException e) {
			e.printStackTrace();
		}
	}

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getTweetTime() {
        return tweetTime;
    }

    public void setTweetTime(String tweetTime) {
        this.tweetTime = tweetTime;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(String retweetCount) {
        this.retweetCount = retweetCount;
    }


    public String getReTwittedBy() {
        return reTwittedBy;
    }

    public void setReTwittedBy(String reTwittedBy) {
        this.reTwittedBy = reTwittedBy;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    public String getMediaHeight() {
        return mediaHeight;
    }

    public void setMediaHeight(String mediaHeight) {
        this.mediaHeight = mediaHeight;
    }

    public String getMediaWidth() {
        return mediaWidth;
    }

    public void setMediaWidth(String mediaWidth) {
        this.mediaWidth = mediaWidth;
    }

    public String getReTwittedByHandle() {
        return reTwittedByHandle;
    }

    public void setReTwittedByHandle(String reTwittedByHandle) {
        this.reTwittedByHandle = reTwittedByHandle;
    }

    public String getTweetCreatedAt() {
        return tweetCreatedAt;
    }

    public void setTweetCreatedAt(String tweetCreatedAt) {
        this.tweetCreatedAt = tweetCreatedAt;
    }

    // Record Finders
	public static TweetModel byId(long id) {
		return new Select().from(TweetModel.class).where("id = ?", id).executeSingle();
	}

	public static List<TweetModel> recentItems() {
		return new Select().from(TweetModel.class).orderBy("id DESC").limit("300").execute();
	}

    @Override
    public View getView(LayoutInflater inflater, View convertView, ViewGroup viewGroup, final Context context) {

        // TODO reuse convertView
        convertView = inflater.inflate(R.layout.tweet, viewGroup, false);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.llretweetlayout);

        if(this.getReTwittedBy()!=null && !this.getReTwittedBy().isEmpty()) {
            TextView reTwittedBy = (TextView) convertView.findViewById(R.id.tvReweetedBy);
            reTwittedBy.setText(getReTwittedBy() + " retweeted");
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
        userName.setText(getUserName());

        final TextView twitterHandle = (TextView) convertView.findViewById(R.id.tvTwitterHandle);
        twitterHandle.setText("@" + getTwitterHandle());

        TextView description = (TextView) convertView.findViewById(R.id.tvDescription);
        description.setText(getTweet());

        ImageView ivMedia = (ImageView) convertView.findViewById(R.id.ivTweetMedia);
        if(getMediaURL()!=null && !getMediaURL().isEmpty()) {
            ivMedia.setImageResource(0);
            ivMedia.getLayoutParams().height = Integer.parseInt(getMediaHeight());
            ivMedia.getLayoutParams().width = Integer.parseInt(getMediaWidth());
            Picasso.with(context).load(getMediaURL()).into(ivMedia);
        }
        else {
            ivMedia.setImageResource(0);
            ivMedia.getLayoutParams().height = 0;
            ivMedia.getLayoutParams().width = 0;
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivUserPhoto);
        imageView.setImageResource(0);
        Picasso.with(context).load(getUserImageURL()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("screen_name",getTwitterHandle());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        TextView favoriteCount = (TextView) convertView.findViewById(R.id.tvFavoriteCount);
        favoriteCount.setText(getFavoriteCount());

        TextView reTweetCount = (TextView) convertView.findViewById(R.id.tvRetweetCount);
        reTweetCount.setText(getRetweetCount());

        TextView timeDiff = (TextView) convertView.findViewById(R.id.tvTimeDiff);

        long time = Long.parseLong(getTweetTime())/1000;
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
                android.support.v4.app.FragmentManager fm = ((TwitterActivity)context).getSupportFragmentManager();
                DialogFragment replyDialogFragment = PostTweetDialog.newInstance("Reply Tweet");
                Bundle args = new Bundle();
                args.putLong("in_reply_to", Long.parseLong(getTweetId()));
                String text = "@" + getTwitterHandle() + " ";
                if(getReTwittedByHandle()!=null && !getReTwittedByHandle().isEmpty()) {
                    text += "@" + getReTwittedByHandle() + " ";
                }
                args.putString("text", text);
                replyDialogFragment.setArguments(args);
                replyDialogFragment.show(fm, "Reply Tweet");
            }
        });
        return convertView;
    }

    @Override
    public int getViewType() {
        return 0;
    }

}
