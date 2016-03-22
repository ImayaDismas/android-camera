package com.dismas.imaya.android_camera;


import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by irving on 2/14/16.
 */
public class ShtakiApplication extends Application {
    Firebase myFirebaseRef;

    @Override
    public void onCreate(){
       super.onCreate();
       Firebase.setAndroidContext(getApplicationContext());
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
       myFirebaseRef = new Firebase("https://android-camera.firebaseio.com/#-K8b2vhJm_HHMaPcqc8E|ecb691d4b54f4313b90f104b2fe52aa7");





    }
}
