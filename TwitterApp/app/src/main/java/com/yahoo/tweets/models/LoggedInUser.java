package com.yahoo.tweets.models;

import java.io.Serializable;

/**
 * Created by rakeshch on 2/8/15.
 */
public class LoggedInUser implements Serializable {
    private String profileURL;
    private String userName;
    private String screenName;

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}
