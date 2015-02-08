package com.yahoo.tweets.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.yahoo.tweets.R;
import com.yahoo.tweets.TwitterApp;
import com.yahoo.tweets.utils.DialogCallBack;
import com.yahoo.tweets.utils.TwitterClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by rakeshch on 2/8/15.
 */
public class PostTweetDialog extends DialogFragment {

    private String userName;
    private String screenName;
    private String profileURL;
    private DialogCallBack callBack;

    public static PostTweetDialog newInstance(String title) {
        PostTweetDialog dialogFragment = new PostTweetDialog();
        Bundle arguments = new Bundle();
        arguments.putString("title", title);
        dialogFragment.setArguments(arguments);
        return dialogFragment;
    }

    @Override
    public void onAttach(Activity a){
        super.onAttach(a);
        callBack = (DialogCallBack) a;
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
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.setCanceledOnTouchOutside(true);
        

        return dialog;
    }

    private void fetchLoggedInUserDetails(final TextView tvUserName, final TextView tvScreenName, final ImageView ivUserPhoto) {

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if(response.length() >= 1) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(0);
                        userName = jsonObject.getJSONObject("user").getString("name");
                        screenName = jsonObject.getJSONObject("user").getString("screen_name");
                        profileURL = jsonObject.getJSONObject("user").getString("profile_image_url");
                        tvUserName.setText(userName);
                        ivUserPhoto.setImageResource(0);
                        Picasso.with(getActivity().getBaseContext()).load(profileURL).into(ivUserPhoto);
                        tvScreenName.setText("@" + screenName);
                    }
                    catch(JSONException e) {

                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        };

        (new TwitterClient(getActivity().getBaseContext())).getUserTimeline(jsonHttpResponseHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Long in_reply_to_id =  getArguments().getLong("in_reply_to");
        final String text = getArguments().getString("text");

        View view = inflater.inflate(R.layout.activity_post_tweet, container);

        final TextView tv = (TextView) view.findViewById(R.id.tvNumCharsLeft);

        final EditText etMyTweet = (EditText)view.findViewById(R.id.etMyTweet);
        etMyTweet.setText(text);

        final int length = 140 - etMyTweet.getText().length();
        tv.setText(length+"|");

        etMyTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv.setText((140-s.length()) + "|");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etMyTweet.setSelection(text.length());
        TextView tvUserName = (TextView) view.findViewById(R.id.tvMyName);
        TextView tvTwitterHandle = (TextView) view.findViewById(R.id.tvMyTwitterHandle);

        ImageView imageView = (ImageView) view.findViewById(R.id.ivMyPhoto);

        Button tweetButton = (Button) view.findViewById(R.id.ibTweet);
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        onPost();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(getActivity().getBaseContext(),"failed posting", Toast.LENGTH_SHORT).show();
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                };
                TwitterApp.getRestClient().postTweet(jsonHttpResponseHandler,etMyTweet.getText().toString(),in_reply_to_id);
            }
        });

        fetchLoggedInUserDetails(tvUserName, tvTwitterHandle, imageView);
        return view;

    }

    public void onPost() {
        callBack.onTweetPost();
        dismiss();
    }
}
