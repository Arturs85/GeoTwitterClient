package com.example.user.geotwitterclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class TweetTestActivity extends Activity
{
private ListView listView;
private Button postPoga;
  //  PostTweetKlase postTweet;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.reloadButton)
        {
            try {
                new LoadListTask().execute();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        return true;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         listView = (ListView) findViewById(R.id.list);
       postPoga = (Button)findViewById(R.id.postPoga);
        postPoga.setOnClickListener(postListener);

        ArrayList<Tweet> listItems = new ArrayList<Tweet>();

       final ArrayAdapter<Tweet> ar = new ArrayAdapter<Tweet>(this, R.layout.list_item, listItems);
        listView.setAdapter(ar);

        //ListView lv = this.getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {

                //Toast.makeText(getApplicationContext(), ((ArrayAdapter<Tweet>)getListAdapter()).getItem(position).id_str,
                //   Toast.LENGTH_SHORT).show();

                Intent i = new Intent(TweetTestActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("param1", ar.getItem(position));
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        new LoadListTask().execute();
    }

    @Override
    public boolean onNavigateUpFromChild(Activity child) {
        new LoadListTask().execute();

        return super.onNavigateUpFromChild(child);
    }

    private class LoadListTask extends AsyncTask<Void, Tweet, Void> {

        private final ProgressDialog pd = new ProgressDialog(TweetTestActivity.this);

        // can use UI thread here
        @SuppressWarnings("unchecked")
        protected void onPreExecute() {
            ((ArrayAdapter<Tweet>)listView.getAdapter()).clear();
            this.pd.setMessage("Loading List...");
            this.pd.show();
        }

        protected Void doInBackground(Void... ars) {
            // TODO    4 new activity with custom adapter to show schedules
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet("http://mgeotwitter.azurewebsites.net/api/Tweets");

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                JSONArray ja = new JSONArray(client.execute(get, responseHandler));

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    publishProgress(new Tweet(jo.getString("TweetGuid"),jo.getString("TweetText"),jo.getDouble("Longitude"),jo.getDouble("Latitude"),jo.getString("DatePublished")));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            return null;
        }

        @SuppressWarnings("unchecked")
        protected void onProgressUpdate(Tweet... progress) {

            ((ArrayAdapter<Tweet>)listView.getAdapter()).add(progress[0]);
            ((ArrayAdapter<Tweet>)listView.getAdapter()).sort(new Tweet());
        }

        protected void onPostExecute(Void result) {
            pd.dismiss();
        }
    }
    View.OnClickListener postListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //PostTweetKlase postTweet = new PostTweetKlase();
           PostTweetKlase postTweet = new PostTweetKlase(getApplicationContext());
            TextView textField = (TextView)findViewById(R.id.editText);

            if((textField.getText()).length()>0) {
                postTweet.teksts =  textField.getText().toString();
                postTweet.execute();
                new LoadListTask().execute();
textField.setText("");
            }
            else textField.append("Ierakstiet tekstu");
           // Toast toast = Toast.makeText(getApplicationContext(), "Suta", Toast.LENGTH_SHORT);
           // toast.show();
            //toast.setText("Sūta...");
        }
    };

}