package com.ceng319.mediacontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.MediaController;
import android.widget.VideoView;



public class  VideoPlayer extends AppCompatActivity {

    MediaController mediaControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);



        VideoView simpleVideoView = (VideoView) findViewById(R.id.videoView2);

        if (mediaControls == null) {
            // create an object of media controller class
            mediaControls = new MediaController(VideoPlayer.this);
            mediaControls.setAnchorView(simpleVideoView);
        }
        // set the media controller for video view
        simpleVideoView.setMediaController(mediaControls);

        simpleVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));

        simpleVideoView.start(); // start a video

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_quit) {

            finishAndRemoveTask();
        }
        if (id==R.id.action_music)
        {

            finish();
            Intent VideoPlayer = new Intent(this,MainActivity.class);
            startActivity(VideoPlayer);
        }

        return super.onOptionsItemSelected(item);
    }
}
