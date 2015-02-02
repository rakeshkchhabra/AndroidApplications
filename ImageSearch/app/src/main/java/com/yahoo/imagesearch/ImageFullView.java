package com.yahoo.imagesearch;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class ImageFullView extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_view);

        final String url = getIntent().getStringExtra("url");
        int height = getIntent().getIntExtra("height",100);
        int width = getIntent().getIntExtra("width",100);
        Log.i("Dimensions : " , height + " " + width);

        TouchImageView imageView = (TouchImageView) findViewById(R.id.fullImageView);
        imageView.setImageResource(0);
        imageView.getLayoutParams().width = width;
        imageView.getLayoutParams().height = height;

        Picasso.with(this).load(url).into(imageView);

        Button button = (Button) findViewById(R.id.emailButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(android.content.Intent.ACTION_SEND);
                email.setType("text/html");
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"rakeshkchhabra@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Nice picture");
                String html = "<div dir=\"ltr\"><img src=\""+ url + "\" height=\"373\" width=\"497\"><br><br></div>";
                email.putExtra(Intent.EXTRA_TEXT, html);
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_full_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
