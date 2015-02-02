package com.yahoo.imagesearch;

/**
 * Created by rakeshch on 1/31/15.
 */
public class ImageItem {

    public String imageUrl;
    public int tabWidth;
    public int tabHeight;
    public int width;
    public int height;

    @Override
    public String toString() {
        return "ImageItem{" +
                "imageUrl='" + imageUrl + '\'' +
                ", tabWidth=" + tabWidth +
                ", tabHeight=" + tabHeight +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
