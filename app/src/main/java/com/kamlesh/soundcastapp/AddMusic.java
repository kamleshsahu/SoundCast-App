package com.kamlesh.soundcastapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AddMusic extends AppCompatActivity{

    private static final String TAG = "ChooserActivity";
    private static final int PERMISSION_REQUESTS = 1;


    private static final int REQUEST_CHOOSE_SONG = 1001;
    private static final int REQUEST_CHOOSE_IMAGE = 1002;

    private static final String SIZE_PREVIEW = "w:max";
    private ImageView thumbnail;
    private static final String KEY_IMAGE_URI = "com.googletest.firebase.ml.demo.KEY_IMAGE_URI";
    private static final String KEY_IMAGE_MAX_WIDTH =
            "com.googletest.firebase.ml.demo.KEY_IMAGE_MAX_WIDTH";
    private static final String KEY_IMAGE_MAX_HEIGHT =
            "com.googletest.firebase.ml.demo.KEY_IMAGE_MAX_HEIGHT";
    private static final String KEY_SELECTED_SIZE =
            "com.googletest.firebase.ml.demo.KEY_SELECTED_SIZE";

    boolean isLandScape;
    private String selectedSize = SIZE_PREVIEW;


    private Uri imageUri;
    // Max width (portrait mode)
    private Integer imageMaxWidth;
    // Max height (portrait mode)
    private Integer imageMaxHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);



        if (!allPermissionsGranted()) {
            getRuntimePermissions();
        }


       findViewById(R.id.addthumbnail).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startChooseImageIntentForResult();
           }
       });

        findViewById(R.id.SelectSong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChooseSongIntentForResult();
            }
        });


        thumbnail=findViewById(R.id.thumbnailView);
        if (thumbnail == null) {
            Log.d(TAG, "Preview is null");
        }



        isLandScape =
                (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);

        if (savedInstanceState != null) {
            imageUri = savedInstanceState.getParcelable(KEY_IMAGE_URI);
            imageMaxWidth = savedInstanceState.getInt(KEY_IMAGE_MAX_WIDTH);
            imageMaxHeight = savedInstanceState.getInt(KEY_IMAGE_MAX_HEIGHT);
            selectedSize = savedInstanceState.getString(KEY_SELECTED_SIZE);

            if (imageUri != null) {
                tryReloadAndDetectInImage();
            }
        }

    }




    private String[] getRequiredPermissions() {
        try {
            PackageInfo info =
                    this.getPackageManager()
                            .getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] ps = info.requestedPermissions;
            if (ps != null && ps.length > 0) {
                return ps;
            } else {
                return new String[0];
            }
        } catch (Exception e) {
            return new String[0];
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                return false;
            }
        }
        return true;
    }

    private void getRuntimePermissions() {
        List<String> allNeededPermissions = new ArrayList<>();
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                allNeededPermissions.add(permission);
            }
        }

        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this, allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
        }
    }

    private static boolean isPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission granted: " + permission);
            return true;
        }
        Log.i(TAG, "Permission NOT granted: " + permission);
        return false;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(KEY_IMAGE_URI, imageUri);
        if (imageMaxWidth != null) {
            outState.putInt(KEY_IMAGE_MAX_WIDTH, imageMaxWidth);
        }
        if (imageMaxHeight != null) {
            outState.putInt(KEY_IMAGE_MAX_HEIGHT, imageMaxHeight);
        }
        outState.putString(KEY_SELECTED_SIZE, selectedSize);
    }

    private void tryReloadAndDetectInImage() {
        try {
            if (imageUri == null) {
                return;
            }

            // Clear the overlay first


            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

            // Get the dimensions of the View
            //  Pair<Integer, Integer> targetedSize = getTargetedWidthHeight();

            //  int targetWidth = targetedSize.first;
            //  int maxHeight = targetedSize.second;

            int targetWidth=480;
            int maxHeight=480;
            // Determine how much to scale down the image
            float scaleFactor =
                    Math.max(
                            (float) imageBitmap.getWidth() / (float) targetWidth,
                            (float) imageBitmap.getHeight() / (float) maxHeight);

            Bitmap resizedBitmap =
                    Bitmap.createScaledBitmap(
                            imageBitmap,
                            (int) (imageBitmap.getWidth() / scaleFactor),
                            (int) (imageBitmap.getHeight() / scaleFactor),
                            true);

            thumbnail.setImageBitmap(resizedBitmap);



        } catch (IOException e) {
            Log.e(TAG, "Error retrieving saved image");
        }
    }

    private void startChooseImageIntentForResult() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CHOOSE_IMAGE);
    }

    private void startChooseSongIntentForResult() {
       // Intent intent = new Intent();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Song"), REQUEST_CHOOSE_SONG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHOOSE_SONG && resultCode == RESULT_OK) {

            System.out.println("got song data :");
            System.out.println(data.getData());

            try {
                BufferedReader br=new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(data.getData())));
                StringBuilder songdata=new StringBuilder();
                String line="";
                while ((line=br.readLine())!=null){
                    //System.out.println(data1);
                    songdata.append(line);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //      tryReloadAndDetectInImage();
        } else if (requestCode == REQUEST_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            // In this case, imageUri is returned by the chooser, save it.
            imageUri = data.getData();

            try {
                BufferedReader br=new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(data.getData())));
                StringBuilder thumbnaildata=new StringBuilder();
                String line="";
                while ((line=br.readLine())!=null){
                    //System.out.println(data1);
                    thumbnaildata.append(line);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            tryReloadAndDetectInImage();
        }
    }

}
