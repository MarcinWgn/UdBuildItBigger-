package com.wegrzyn.marcin.androidjokeslibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokesActivity extends AppCompatActivity {

    public static final String JOKES_STRING = "jokes_string";
    public static final String TAG = JokesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        TextView jokesView = findViewById(R.id.jokes_tv);


        if (getIntent().hasExtra(JOKES_STRING)) {
            String jokesString = getIntent().getStringExtra(JOKES_STRING);
            jokesView.setText(jokesString);
        }

    }
}
