package com.yahoo.tweets.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * Created by rakeshch on 2/14/15.
 */
public interface ListItem extends Serializable {

    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView, ViewGroup viewGroup, Context context);
}
