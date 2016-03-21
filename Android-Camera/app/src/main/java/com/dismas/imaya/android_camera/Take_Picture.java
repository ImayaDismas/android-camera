package com.dismas.imaya.android_camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Address;
import android.support.v7.widget.RecyclerView;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by imaya on 3/21/16.
 */
public class Take_Picture extends ActionBarActivity {
    Firebase myFirebaseRef;
    SharedPreferences SHTAKIprefferences;
    Button bPost;
    ImageButton photo;
    static final int AVATAR_DIALOG_ID = 2;
    static final int TAKE_AVATAR_CAMERA_REQUEST = 1;
    static final int TAKE_AVATAR_GALLERY_REQUEST = 2;


    List<Address> locationList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_picture);
        setToolBar();
        SHTAKIprefferences = getSharedPreferences(Constants.SHTAKI_PREFERENCES,
                Context.MODE_PRIVATE);
        Firebase.setAndroidContext(getApplicationContext());
        myFirebaseRef = new Firebase("https://android-camera.firebaseio.com/#-K8b2vhJm_HHMaPcqc8E|ecb691d4b54f4313b90f104b2fe52aa7");
        bPost = (Button) findViewById(R.id.SaveImage);
        photo = (ImageButton) findViewById(R.id.TakeImage);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Take_Picture.this.showDialog(AVATAR_DIALOG_ID);


            }
        });

        bPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> SaveImage = new HashMap<String, Object>();
                if(SHTAKIprefferences.contains(Constants.CURRENT_IMAGE)){
                    SaveImage.put("PICTURE",
                            SHTAKIprefferences.getString(Constants.CURRENT_IMAGE, null));

                }else {
                    SaveImage.put("PICTURE","");

                }

                myFirebaseRef.child("REPORTS").push().setValue(SaveImage);
                photo.setImageDrawable(null);
                photo.setImageBitmap(null);
                photo.setImageURI(null);
                photo.setImageDrawable(getResources().getDrawable(R.drawable.index));
                SharedPreferences.Editor erem = SHTAKIprefferences.edit();
                erem.remove(Constants.CURRENT_IMAGE);
                erem.commit();
                Toast.makeText(getApplicationContext(),
                        "The image saved successfully", Toast.LENGTH_SHORT).show();


                Intent i = new Intent(getApplicationContext(), Take_Picture.class);
                startActivity(i);
                finish();

            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_AVATAR_CAMERA_REQUEST:
                if (resultCode == Activity.RESULT_CANCELED) {
                    // Avatar camera mode was canceled.
                } else if (resultCode == Activity.RESULT_OK) {
                    // TODO: HANDLE PHOTO TAKEN
                    Bitmap cameraPic = (Bitmap) data.getExtras().get("data");
                    saveAvatar(cameraPic);
                }
                break;
            case TAKE_AVATAR_GALLERY_REQUEST:
                if (resultCode == Activity.RESULT_CANCELED) {
                    // Avatar gallery request mode was canceled.
                } else if (resultCode == Activity.RESULT_OK) {
                    // TODO: HANDLE IMAGE CHOSEN
                    Uri photoUri = data.getData();
                    try {
                        Bitmap galleryPic = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);

                        saveAvatar(galleryPic);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void saveAvatar(Bitmap avatar) {
// TODO: Save the Bitmap as a local file called avatar.jpg
        String strAvatarFilename = "avatar.jpg";
        try {
            avatar.compress(Bitmap.CompressFormat.JPEG,
                    100, openFileOutput(strAvatarFilename, MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

// TODO: Determine the Uri to the local avatar.jpg file

// TODO: Save the Uri path as a String preference
        Uri imageUri = Uri.fromFile(new File(getFilesDir(), strAvatarFilename));
// TODO: Update the ImageButton with the new image
        photo.setImageBitmap(null);
        photo.setImageDrawable(null);
        photo.setImageBitmap(avatar);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        avatar.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String image = Base64.encodeToString(byteArray, Base64.DEFAULT);
        SharedPreferences.Editor editorwa = SHTAKIprefferences.edit();
        editorwa.putString(Constants.CURRENT_IMAGE, image);
        editorwa.commit();


    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSingleStack);
        if (toolbar != null) {

            setSupportActionBar(toolbar);
            setUpActionbar();
            toolbar.setBackgroundColor(getResources().getColor(R.color.green));
            toolbar.setTitleTextColor(getResources().getColor(R.color.white_pure));


        }

    }

    private void setUpActionbar() {
        if (getSupportActionBar() != null) {
            ActionBar bar = getSupportActionBar();
            bar.setTitle(getResources().getString(R.string.app_name));
            bar.setHomeButtonEnabled(false);
            bar.setDisplayShowHomeEnabled(false);
            bar.setDisplayHomeAsUpEnabled(false);
            bar.setDisplayShowTitleEnabled(true);


            bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        }


    }
}