package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Marcin Węgrzyn on 12.05.2018.
 * wireamg@gmail.com
 */
class JokeEndpointsAsyncTask extends AsyncTask<Void, Void, String> {

    private static MyApi myApiService = null;
    private CallbackInterface callbackInterface;

    private CountingIdlingResource idlingResource = new CountingIdlingResource("loader_data");

    public interface CallbackInterface{
        void onPostTask(String joke);
    }

    public void registerListener(CallbackInterface callbackInterface){
        this.callbackInterface = callbackInterface;
    }

    public void unregisterListener (){
        callbackInterface = null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        IdlingRegistry.getInstance().register(idlingResource);
        idlingResource.increment();
    }

    @Override
    protected final String doInBackground(Void ... voids) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            myApiService = builder.build();
        }
        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        idlingResource.decrement();

        if(callbackInterface != null){
            callbackInterface.onPostTask(result);
        }
    }


}
