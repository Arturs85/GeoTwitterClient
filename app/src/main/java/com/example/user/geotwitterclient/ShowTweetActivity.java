package com.example.user.geotwitterclient;

/**
 * Created by user on 2016.11.29..
 */
import android.app.Activity;
import android.os.Bundle;

public class ShowTweetActivity extends Activity
{
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.main);

        Bundle bundle = this.getIntent().getExtras();
        Tweet param1 = (Tweet) bundle.getSerializable("param1");

       // TextView b = (TextView) findViewById(R.id.textViewTweet);
        //b.setText(param1.message + "\n Long: "+param1.longitude+ " Lat: "+param1.latitude+"\n"+param1.dateString);
    }
}
