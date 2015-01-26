package com.example.rakeshch.instagrapmpopular;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakeshch on 1/25/15.
 */
public class PhotoModel {
    public String userName;
    public String caption;
    public int imgHeight;
    public String imageUrl;
    public int likesCount;
    public String userPhotoUrl;
    public PhotoComment photoComment1;
    public PhotoComment photoComment2;
    public int commentCount;
    public String createdTime;
    public ArrayList<PhotoComment> photoComments = new ArrayList<>();
}