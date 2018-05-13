package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Marcin Węgrzyn on 13.05.2018.
 * wireamg@gmail.com
 */
@RunWith(AndroidJUnit4.class)
public class JokeEndpointsAsyncTaskTest {

    @Test
    public void getJokeStringTest(){
        new JokeEndpointsAsyncTask(new JokeEndpointsAsyncTask.CallbackInterface() {
            @Override
            public void onPostTask(String joke) {
                assertTrue(!TextUtils.isEmpty(joke));
            }
        }).execute();
    }
}
