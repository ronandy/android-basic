package com.ronandy.getTweets;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronandy on 7/20/15.
 */
public class JSONResponseHandler implements ResponseHandler<List<Tweet>> {

    private static final String DATE_TAG = "created_at";
    private static final String NAME_TAG = "name";
    private static final String SCREEN_NAME_TAG = "screen_name";
    private static final String TEXT_TAG = "text";
    private static final String TWEETS_TAG = "user";

    @Override
    public List<Tweet> handleResponse(HttpResponse response) throws IOException {
        List<Tweet> result = new ArrayList<Tweet>();
        String JSONResponse = new BasicResponseHandler().handleResponse(response);
        System.out.println("Salida del response: " + JSONResponse);
        try {
            JSONArray responseObject = new JSONArray(JSONResponse);

            for (int i = 0; i < responseObject.length(); i++) {
                JSONObject tweets = (JSONObject) responseObject.get(i);
                String created = tweets.getString(DATE_TAG);
                String text = tweets.getString(TEXT_TAG);

                //interior del objeto user
                JSONObject userData = tweets.getJSONObject(TWEETS_TAG);
                String screen_name = userData.getString(SCREEN_NAME_TAG);
                String name = userData.getString(NAME_TAG);

                Tweet tweet = new Tweet(created, screen_name, name, text);
                result.add(tweet);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
