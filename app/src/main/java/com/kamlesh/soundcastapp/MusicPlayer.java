package com.kamlesh.soundcastapp;

import android.Manifest;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kamlesh.soundcastapp.Adapters.AdapterList;
import com.kamlesh.soundcastapp.Model.Apidata;
import com.kamlesh.soundcastapp.Model.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.kamlesh.soundcastapp.HomeScreen.apidata;

public class MusicPlayer extends AppCompatActivity {
    int currposs=0;
    Button prev,play,next,download;
    DownloadManager downloadManager;
    private long refid;
    private Uri Download_Uri;
    MediaPlayer mediaPlayer= new MediaPlayer();
    ArrayList<Long> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
         prev=findViewById(R.id.prev);
         play=findViewById(R.id.play);
         next=findViewById(R.id.next);
         download=findViewById(R.id.download);

        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


        currposs = getIntent().getIntExtra("currposition",0);

        System.out.printf("Song details : %s ",new Gson().toJson(apidata.getResults().get(currposs)));
        updateUI();

        if(!isStoragePermissionGranted())
        {

        }
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currposs-1>=0){
                    currposs-=1;
                    updateUI();
                }else Toast.makeText(getApplicationContext(),"no prev",Toast.LENGTH_LONG).show();
            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // getExternalFilesDir("SoundCast/"  + apidata.getResults().get(currposs).getMusic_file().getName());

                if (!mediaPlayer.isPlaying()) {

                //    Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
            //     Uri uri = Uri.parse(new File( "/SoundCast/"  + apidata.getResults().get(currposs).getMusic_file().getName()));
                    Uri uri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/SoundCast/"+apidata.getResults().get(currposs).getMusic_file().getName()));

                    //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    System.out.println("\n\nhere is url : "+uri);
                    try {
                        mediaPlayer.setDataSource(getApplicationContext(), uri);
                        mediaPlayer.prepare();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mediaPlayer.start();
                                // Toast.makeText(c, "We are in play media", Toast.LENGTH_SHORT).show();
                                // mediaPlayer.setLooping(true);
                                //changeNotificationState();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    mediaPlayer.reset();

                //    Uri muri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, apidata.getResults().get(currposs).getMusic_file().getName());
                    Uri muri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/SoundCast/"+apidata.getResults().get(currposs).getMusic_file().getName()));

                      mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mediaPlayer.setDataSource(getApplicationContext(), muri);
                        mediaPlayer.prepare();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mediaPlayer.start();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                list.clear();
                Download_Uri = Uri.parse(apidata.getResults().get(currposs).getMusic_file().getUrl());
                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setTitle(apidata.getResults().get(currposs).getTitle());
                request.setDescription(apidata.getResults().get(currposs).getObjectId());
                request.setVisibleInDownloadsUi(true);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/SoundCast/"  + apidata.getResults().get(currposs).getMusic_file().getName());


                refid = downloadManager.enqueue(request);


                Log.e("OUT", "" + refid);

                list.add(refid);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currposs+1<apidata.getResults().size()){
                    currposs+=1;
                    updateUI();
                }else Toast.makeText(getApplicationContext(),"no next",Toast.LENGTH_LONG).show();
            }
        });
    }

    void updateUI(){
        Glide.with(MusicPlayer.this)
                .load(apidata.getResults().get(currposs).getThumbnail())
                //     .override(200,200) // resizes the image to 100x200 pixels but does not respect aspect ratio
                .into((ImageView) findViewById(R.id.thumbnail));
        ((TextView)findViewById(R.id.title)).setText(apidata.getResults().get(currposs).getTitle());

        Uri uri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/SoundCast/"+apidata.getResults().get(currposs).getMusic_file().getName()));

        if(uri==null){
            play.setEnabled(false);
            download.setText("Download");
            download.setEnabled(true);
        }else{
            play.setEnabled(true);
            download.setText("Downloaded");
            download.setEnabled(false);
        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }





    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {




            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            Log.e("IN", "" + referenceId);

            list.remove(referenceId);


            if (list.isEmpty())
            {


                Log.e("INSIDE", "" + referenceId);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MusicPlayer.this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("GadgetSaint")
                                .setContentText("All Download completed");


                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());


            }

        }
    };




    @Override
    protected void onDestroy() {


        super.onDestroy();

        unregisterReceiver(onComplete);



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            // permission granted

        }
    }
}
