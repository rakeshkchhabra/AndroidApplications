package com.example.rakeshch.instagrapmpopular;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rakeshch on 1/25/15.
 */
public class PhotoAdapater extends ArrayAdapter<PhotoModel> {

    public PhotoAdapater(Context context, List<PhotoModel> photoModelList) {
        super(context,R.layout.photolayout, photoModelList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        com.squareup.picasso.Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.CYAN)
                .borderWidthDp(1)
                .cornerRadiusDp(2)
                .oval(true)
                .build();

        final PhotoModel photoModel = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.photolayout, parent, false);
        }
        TextView imgCaption = (TextView) convertView.findViewById(R.id.imgCaption);
        TextView imgUsername = (TextView) convertView.findViewById(R.id.imgUsername);
        ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
        ImageView imgUserPhoto = (ImageView) convertView.findViewById(R.id.imgUser);
        TextView imgLikes = (TextView) convertView.findViewById(R.id.countLike);

        ImageView imgCommentor1 = (ImageView) convertView.findViewById(R.id.imgComment1);
        ImageView imgCommentor2 = (ImageView) convertView.findViewById(R.id.imgComment2);
        TextView comment1 = (TextView) convertView.findViewById(R.id.comment1);
        TextView comment2 = (TextView) convertView.findViewById(R.id.comment2);
        TextView commentor1 = (TextView) convertView.findViewById(R.id.commentor1);
        TextView commentor2 = (TextView) convertView.findViewById(R.id.commentor2);
        TextView commentCount = (TextView) convertView.findViewById(R.id.commentCount);
        TextView createdTime = (TextView) convertView.findViewById(R.id.timediff);

        imgLikes.setText(photoModel.likesCount + " likes");
        imgCaption.setText(photoModel.caption);
        imgUsername.setText(photoModel.userName);
        imgPhoto.getLayoutParams().height = photoModel.imgHeight;
        imgPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photoModel.imageUrl).into(imgPhoto);

        imgUserPhoto.getLayoutParams().height = 100;
        imgUserPhoto.getLayoutParams().width = 100;
        imgUserPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photoModel.userPhotoUrl).transform(transformation).into(imgUserPhoto);

        imgCommentor1.getLayoutParams().height = 100;
        imgCommentor1.getLayoutParams().width = 100;
        imgCommentor1.setImageResource(0);
        imgCommentor2.getLayoutParams().height = 100;
        imgCommentor2.getLayoutParams().width = 100;
        imgCommentor2.setImageResource(0);
        Picasso.with(getContext()).load(photoModel.photoComment1.commentorProfileUrl).transform(transformation).into(imgCommentor1);
        Picasso.with(getContext()).load(photoModel.photoComment2.commentorProfileUrl).transform(transformation).into(imgCommentor2);
        comment1.setText(photoModel.photoComment1.comment);
        comment2.setText(photoModel.photoComment2.comment);
        commentor1.setText(photoModel.photoComment1.commentor);
        commentor2.setText(photoModel.photoComment2.commentor);
        commentCount.setText("See all " + photoModel.commentCount + " Comments.");

        commentCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = ((MainActivity) getContext()).fetchFragmentManager();
                CommentDialogFragment commentDialog = CommentDialogFragment.newInstance("Comments", photoModel.photoComments);
                commentDialog.show(fm, "fragment_comments");
            }
        });

        long time = Long.parseLong(photoModel.createdTime);
        long now = System.currentTimeMillis()/1000;

        if(now-time < 3600) {
            createdTime.setText( "-" + (now-time)/60 + "m ");
        }
        else if(now-time < 86400) {
            createdTime.setText( "-" +(now-time)/3600 + "h ");
        }
        else {
            createdTime.setText( "-" +(now-time)/86400 + "d ");
        }
        return convertView;
    }
}
