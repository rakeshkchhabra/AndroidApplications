package com.yahoo.tweets.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.yahoo.tweets.R;
import com.yahoo.tweets.adapters.PostTweetDialog;
import com.yahoo.tweets.adapters.UserAdapter;
import com.yahoo.tweets.models.ProfileModel;
import com.yahoo.tweets.utils.TwitterRestClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakeshch on 2/19/15.
 */
public class UsersListFragment extends DialogFragment {

    private List<ProfileModel> profileModels;
    private UserAdapter userAdapter;
    private String  handle;
    private static int mode = 0;

    private UsersListFragment(int mode, String handle) {
        this.mode = mode;
        this.handle = handle;
    }

    public static UsersListFragment newInstance(String title, int mode,String handle) {
        UsersListFragment dialogFragment = new UsersListFragment(mode,handle);
        Bundle arguments = new Bundle();
        arguments.putString("title", title);
        dialogFragment.setArguments(arguments);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Holo_NoActionBar_TranslucentDecor);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        final View view = getActivity().getLayoutInflater().inflate(R.layout.activity_post_tweet, null);
        final Drawable d = new ColorDrawable(Color.WHITE);
        //d.setAlpha(130);
        dialog.getWindow().setBackgroundDrawable(d);
        dialog.getWindow().setContentView(view);
        final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = 1200;
        params.gravity = Gravity.CENTER;
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<ProfileModel> profileModels = new ArrayList<>();
        userAdapter = new UserAdapter(getActivity().getBaseContext(),profileModels);

        View view = inflater.inflate(R.layout.users, container);
        TextView tvHeader = (TextView)view.findViewById(R.id.tvFollowersOrFriends);

        if(mode == 0) {
            TwitterRestClient.fetchUserFollowers(profileModels, userAdapter, handle);
            tvHeader.setText("Followers");
        }
        else {
            TwitterRestClient.fetchUserFriends(profileModels,userAdapter,handle);
            tvHeader.setText("Followings");
        }


        ListView lvUsers = (ListView)view.findViewById(R.id.lvUsers);
        lvUsers.setAdapter(userAdapter);


        return view;
    }
}
