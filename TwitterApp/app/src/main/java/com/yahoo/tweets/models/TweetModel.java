package com.yahoo.tweets.models;

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

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the ActiveAndroid wiki for more details:
 * https://github.com/pardom/ActiveAndroid/wiki/Creating-your-database-model
 * 
 */
@Table(name = "items")
public class TweetModel extends Model implements Serializable {
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
}
