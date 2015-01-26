package com.example.rakeshch.instagrapmpopular;

/**
 * Created by rakeshch on 1/26/15.
 */

import android.os.Bundle;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CommentDialogFragment extends DialogFragment {
    private static ArrayList<PhotoComment> commentData;

    public static CommentDialogFragment newInstance(String title, ArrayList<PhotoComment> comments) {
        CommentDialogFragment frag = new CommentDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        commentData = comments;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comments_dialog, container);
        CommentsAdapter adapter = new CommentsAdapter(getActivity(), commentData);
        ListView lvComments = (ListView) view.findViewById(R.id.commentsList);
        lvComments.setAdapter(adapter);
        String title = getArguments().getString("title", "Comments");
        getDialog().setTitle(title);
        getDialog().setCanceledOnTouchOutside(true);
        return view;
    }
}