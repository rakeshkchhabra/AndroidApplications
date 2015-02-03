package com.yahoo.imagesearch;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rakeshch on 1/31/15.
 */
public class ImageAdapter extends ArrayAdapter<ImageItem> {

   public ImageAdapter(Context context, List<ImageItem> imageItems) {
       super(context,R.layout.search_image, imageItems);
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("Debug", "Coming here");

        final ImageItem imageItem = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_image, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        imageView.setImageResource(0);
       // imageView.getLayoutParams().width = imageItem.tabWidth;
       // imageView.getLayoutParams().height = imageItem.tabHeight;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ImageFullView.class);
                intent.putExtra("url",imageItem.imageUrl);
                intent.putExtra("height",imageItem.height);
                intent.putExtra("width",imageItem.width);
                intent.putExtra("title", imageItem.title);

                getContext().startActivity(intent);
            }
        });
        Log.i("Debug", "Lading " + imageItem.tbImageUrl);
        Picasso.with(getContext()).load(imageItem.tbImageUrl).into(imageView);
        return convertView;
    }
}
