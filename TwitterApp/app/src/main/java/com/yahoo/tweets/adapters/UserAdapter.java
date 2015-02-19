package com.yahoo.tweets.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.tweets.R;
import com.yahoo.tweets.activities.ProfileActivity;
import com.yahoo.tweets.models.ProfileModel;

import java.util.List;

/**
 * Created by rakeshch on 2/19/15.
 */
public class UserAdapter extends ArrayAdapter<ProfileModel> {

    public UserAdapter(Context context, List<ProfileModel> profileModels) {
        super(context, R.layout.user, profileModels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ProfileModel profileModel = getItem(position);

        Log.i("DEBUG",profileModel.toString());
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivUserPhoto);
        imageView.setImageResource(0);
        Picasso.with(getContext()).load(profileModel.getProfileImageURL()).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("screen_name",profileModel.getProfileHandle());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        tvUserName.setText(profileModel.getProfileName());

        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvTwitterHandle);
        tvScreenName.setText("@" + profileModel.getProfileHandle());

        TextView tvDesc = (TextView) convertView.findViewById(R.id.tvDescription);
        tvDesc.setText(profileModel.getProfileDescription());

        return convertView;
    }
}
