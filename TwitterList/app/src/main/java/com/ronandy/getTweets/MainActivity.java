package com.ronandy.getTweets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView txtUser;
    private TextView txtCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadTweetsJSON(View v) {
        Intent intent = new Intent(MainActivity.this, TwitterList.class);
        txtUser = (TextView) findViewById(R.id.user);
        txtCount = (TextView) findViewById(R.id.count);
        intent.putExtra("USER", txtUser.getText().toString());
        intent.putExtra("COUNT", txtCount.getText().toString());
        startActivity(intent);
    }
}
