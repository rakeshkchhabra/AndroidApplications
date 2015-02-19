package com.yahoo.tweets.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by rakeshch on 2/14/15.
 */
public class ProfileModel {

    private String profileImageURL;
    private String profileName;
    private String profileHandle;
    private int twitterCount;
    private int followingCount;
    private int followersCount;
    private int profileHeight;
    private int profileWidth;
    private String profileDescription;
    private String bannerImageURL;

    // Parse model from JSON
    public ProfileModel(JSONObject object){
        super();
        Log.i("DEBUG1", object.toString());
        try {
            profileName = object.getString("name");
            profileHandle = object.getString("screen_name");
            profileImageURL = object.getString("profile_image_url_https");
            followingCount = object.getInt("friends_count");
            followersCount = object.getInt("followers_count");
            twitterCount = object.getInt("statuses_count");
            bannerImageURL = object.getString("profile_banner_url") + "/mobile";
            profileDescription = object.getString("description");

        } catch (JSONException e) {

        }
    }


    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getTwitterCount() {
        return twitterCount;
    }

    public void setTwitterCount(int twitterCount) {
        this.twitterCount = twitterCount;
    }

    public String getProfileHandle() {
        return profileHandle;
    }

    public void setProfileHandle(String profileHandle) {
        this.profileHandle = profileHandle;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getProfileHeight() {
        return profileHeight;
    }

    public void setProfileHeight(int profileHeight) {
        this.profileHeight = profileHeight;
    }

    public int getProfileWidth() {
        return profileWidth;
    }

    public void setProfileWidth(int profileWidth) {
        this.profileWidth = profileWidth;
    }


    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public String getBannerImageURL() {
        return bannerImageURL;
    }

    public void setBannerImageURL(String bannerImageURL) {
        this.bannerImageURL = bannerImageURL;
    }

    @Override
    public String toString() {
        return "ProfileModel{" +
                "profileImageURL='" + profileImageURL + '\'' +
                ", profileName='" + profileName + '\'' +
                ", profileHandle='" + profileHandle + '\'' +
                ", twitterCount=" + twitterCount +
                ", followingCount=" + followingCount +
                ", followersCount=" + followersCount +
                ", profileHeight=" + profileHeight +
                ", profileWidth=" + profileWidth +
                ", profileDescription='" + profileDescription + '\'' +
                ", bannerImageURL='" + bannerImageURL + '\'' +
                '}';
    }
}
