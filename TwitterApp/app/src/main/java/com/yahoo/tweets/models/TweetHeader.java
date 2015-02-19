package com.yahoo.tweets.models;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.tweets.R;
import com.yahoo.tweets.activities.ProfileActivity;
import com.yahoo.tweets.activities.UsersListFragment;
import com.yahoo.tweets.adapters.PostTweetDialog;
import com.yahoo.tweets.models.ListItem;

/**
 * Created by rakeshch on 2/14/15.
 */
public class TweetHeader implements ListItem {

    ProfileModel profileModel;
    public TweetHeader(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView, ViewGroup viewGroup, final Context context) {

        // TODO reuse convertView
        convertView = inflater.inflate(R.layout.profile_header, viewGroup, false);
        ImageView ivBannerImage = (ImageView)convertView.findViewById(R.id.ivBannerImage);
        ivBannerImage.setImageResource(0);
        Picasso.with(context).load(profileModel.getBannerImageURL()).into(ivBannerImage);

        TextView tvTweetCount = (TextView)convertView.findViewById(R.id.tvTweetsCount);
        tvTweetCount.setText(profileModel.getTwitterCount() + "");

        TextView tvFollowersCount = (TextView)convertView.findViewById(R.id.tvFollowersCount);
        tvFollowersCount.setText(profileModel.getFollowersCount()  + "");

        TextView tvFollowingCount = (TextView)convertView.findViewById(R.id.tvFollowingCount);
        tvFollowingCount.setText(profileModel.getFollowingCount() + "");

        ImageView ivProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);
        ivProfileImage.setImageResource(0);
        Picasso.with(context).load(profileModel.getProfileImageURL()).into(ivProfileImage);

        TextView tvUserName = (TextView)convertView.findViewById(R.id.tvUserName);
        tvUserName.setText(profileModel.getProfileName());

        TextView tvScreenName = (TextView)convertView.findViewById(R.id.tvScreenName);
        tvScreenName.setText("@" + profileModel.getProfileHandle());

        TextView tvDescription = (TextView)convertView.findViewById(R.id.tvUserDescription1);
        tvDescription.setText(profileModel.getProfileDescription());

        tvFollowersCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = ((ProfileActivity)context).getSupportFragmentManager();
                UsersListFragment tweetDialogFragment = UsersListFragment.newInstance("Followers",0);
                tweetDialogFragment.show(fm, "Followers");
            }
        });

        tvFollowingCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = ((ProfileActivity)context).getSupportFragmentManager();
                UsersListFragment tweetDialogFragment = UsersListFragment.newInstance("Followings",1);
                tweetDialogFragment.show(fm, "Followings");
            }
        });

        return convertView;
    }
}
