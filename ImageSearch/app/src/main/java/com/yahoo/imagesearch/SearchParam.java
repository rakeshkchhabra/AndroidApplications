package com.yahoo.imagesearch;

import java.io.Serializable;

/**
 * Created by rakeshch on 1/31/15.
 */
public class SearchParam implements Serializable {

    public String queryString;
    public int imageTypePosition = 0;
    public String siteFilter = "";
    public int colorFilterPosition = 0;
    public int imageSizePosition = 0;
    public String ip;
    public int label;
    public int start;
}
