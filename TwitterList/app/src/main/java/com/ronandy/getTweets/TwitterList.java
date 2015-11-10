package com.ronandy.getTweets;

import android.app.ListActivity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.util.List;

/**
 * Created by ronandy on 7/20/15.
 */
public class TwitterList extends ListActivity {

    public String user = "";
    public String count = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = getIntent().getStringExtra("USER");
        count = getIntent().getStringExtra("COUNT");
        new LoadEarthQuakes(user,count).execute();
    }

    private class LoadEarthQuakes extends AsyncTask<Void, Void, List<Tweet>> {
        private String URL_JSON;

        AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

        public LoadEarthQuakes(String user, String count) {
            String params = "";
            if(!user.equals("") && !count.equals(""))
                params = "?user="+user+"&count="+count;
            if(!user.equals("") && count.equals(""))
                params ="?user="+user;
            if(user.equals("") && !count.equals(""))
                params ="?count="+count;
            URL_JSON = "http://www.iese.umss.edu.bo/getTweets/showTweets.php"+params;
        }

        @Override
        protected List<Tweet> doInBackground(Void... params) {
             return loadFromJSON();
        }

        private List<Tweet> loadFromJSON() {
            System.out.println("Url de consulta: "+ URL_JSON);
            HttpGet request = new HttpGet(URL_JSON);
            JSONResponseHandler responseHandler = new JSONResponseHandler();
            try {
                return mClient.execute(request, responseHandler);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Tweet> result) {
            if (null != mClient)
                mClient.close();

            setListAdapter(new ArrayAdapter<Tweet>(TwitterList.this, R.layout.list_item, result));
        }
    }

}
