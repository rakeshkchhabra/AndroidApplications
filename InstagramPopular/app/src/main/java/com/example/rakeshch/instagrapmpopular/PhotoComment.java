package com.example.rakeshch.instagrapmpopular;

/**
 * Created by rakeshch on 1/26/15.
 */
public class PhotoComment {
    public String commentor;
    public String commentorProfileUrl;
    public String comment;

    public PhotoComment(String commentor, String commentorProfileUrl, String comment) {
        this.commentor = commentor;
        this.commentorProfileUrl = commentorProfileUrl;
        this.comment = comment;
    }
}
