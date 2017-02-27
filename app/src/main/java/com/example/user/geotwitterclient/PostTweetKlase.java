package com.example.user.geotwitterclient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by user on 2016.12.05..
 */
public class PostTweetKlase extends AsyncTask<Void,Void,String>{
Context context;
    String teksts;
    PostTweetKlase(Context context){
   this.context = context;

}
    @Override
    protected String doInBackground(Void... params) {

        try {
            URL url = new URL("http://mgeotwitter.azurewebsites.net/api/Tweets"); //Enter URL here
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
           // URLConnection httpURLConnection = url.openConnection();
            httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
            httpURLConnection.addRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            //httpURLConnection.connect();
            httpURLConnection.setDoOutput(true);
            String  guid = UUID.randomUUID().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("TweetGuid", guid);
            jsonObject.put("TweetText", teksts);
            jsonObject.put("DatePublished",currentDateandTime);
            jsonObject.put("Latitude", "33");
            jsonObject.put("Longitude", "55");
            //Toast.makeText(context, jsonObject.toString(), Toast.LENGTH_LONG).show();
            String pa =new String("{\"TweetGuid\":\"aa9df9f7-8aa1-4ee0-abe8-4328c4e8c8e9\",\"TweetText\":\"android \","
                    + "\"Latitude\":\"23\",\"Longitude\":\"46\",\"DatePublished\":\"2016-12-08\"}");
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter wr2 = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            //wr.writeBytes(jsonObject.toString());


            wr2.write(jsonObject.toString());
            wr2.flush();

            BufferedReader serverAnswer = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = serverAnswer.readLine()) != null) {
                Log.v("PostTweet", line);
 //<--If any response from server
                //use it as you need, if server send something back you will get it here.
            }
            wr2.close();

            serverAnswer.close();
os.close();
            httpURLConnection.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.v("PostTweet","er1");
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("PostTweet", "er2");

        } catch (JSONException e) {
            e.printStackTrace();
            Log.v("PostTweet", "er3");

        }

        return null;
    }
    @Override
      protected void onPostExecute(String result) {
         Toast.makeText(context, "Data Sent!", Toast.LENGTH_LONG).show();
       }



}
