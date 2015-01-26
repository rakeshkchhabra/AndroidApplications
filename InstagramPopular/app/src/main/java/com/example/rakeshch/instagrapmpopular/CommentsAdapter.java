package com.example.rakeshch.instagrapmpopular;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rakeshch on 1/26/15.
 */
public class CommentsAdapter extends ArrayAdapter<PhotoComment> {

    public CommentsAdapter(Context context, ArrayList<PhotoComment> comments) {
        super(context, R.layout.comment_layout, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        com.squareup.picasso.Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.CYAN)
                .borderWidthDp(1)
                .cornerRadiusDp(2)
                .oval(true)
                .build();

        PhotoComment comment = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_layout, parent, false);
        }
        TextView commentView = (TextView) convertView.findViewById(R.id.comment);
        TextView commentUser = (TextView) convertView.findViewById(R.id.commentor);
        ImageView imgCommentor = (ImageView) convertView.findViewById(R.id.imgComment);

        commentView.setText(comment.comment);
        commentUser.setText(comment.commentor);
        Picasso.with(getContext()).load(comment.commentorProfileUrl).transform(transformation).into(imgCommentor);

        return convertView;
    }

}
