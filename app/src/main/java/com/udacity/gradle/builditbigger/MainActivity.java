package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.wegrzyn.marcin.androidjokeslibrary.JokesActivity;

public class MainActivity extends AppCompatActivity {


    private JokeEndpointsAsyncTask task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        task = new JokeEndpointsAsyncTask();
        task.registerListener(new JokeEndpointsAsyncTask.CallbackInterface() {
            @Override
            public void onPostTask(String joke) {
                Intent intent = new Intent(getBaseContext(), JokesActivity.class);
                intent.putExtra(JokesActivity.JOKES_STRING, joke);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        task.unregisterListener();
        super.onDestroy();
    }

    public void tellJoke(View view) {
        task.execute();
    }

}
