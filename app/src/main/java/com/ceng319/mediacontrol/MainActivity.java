package com.ceng319.mediacontrol;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //declaring the variables and its value;
    private MediaPlayer mediaPlayer;
    private TextView songName, songDuration;
    private SeekBar seekBar;
    private double timeStart = 0, finalTime = 0;
    private int forwardTime = 5000, backwardTime = 5000;
    private Handler durationHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        songName = (TextView) findViewById(R.id.VideoName); //Finds the name for the current media playing
        songDuration = (TextView) findViewById(R.id.songDuration); //For the duration of media
        mediaPlayer = MediaPlayer.create(this, R.raw.music); //sets the media into player
        seekBar = (SeekBar) findViewById(R.id.seekBar); //sets the seek bar aka progress bar
        songName.setText("Song.mp3"); //sets the title
        seekBar.setMax((int) finalTime); //sets the max time to 0:00
        seekBar.setClickable(false); //makes seek bar not-clickable
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            mediaPlayer.stop();     //stops the mediaplayer
            finishAndRemoveTask();
        }
        if (id==R.id.action_video)
        {
            mediaPlayer.stop();
            finish();
            Intent VideoPlayer = new Intent(this,VideoPlayer.class);    //Goes to viedo player
            startActivity(VideoPlayer);
        }

        return super.onOptionsItemSelected(item);
    }
    public void pause(View view) {
        mediaPlayer.pause();
    }

    public void play(View view) {
        mediaPlayer.start();
        timeStart = mediaPlayer.getCurrentPosition();
        seekBar.setProgress((int) timeStart);
        durationHandler.postDelayed(updateSeekBarTime, 100);
    }
    public void forward(View view) {
        if ((timeStart + forwardTime) <= 0) {
            timeStart = mediaPlayer.getCurrentPosition() + forwardTime;
            mediaPlayer.seekTo((int) timeStart);
        }
    }

    public void backforward(View view) {
        if ((timeStart - backwardTime) > 0) {
            timeStart = mediaPlayer.getCurrentPosition() - backwardTime;
            mediaPlayer.seekTo((int) timeStart);
        }
    }

    public void stop(View view) {

        mediaPlayer.stop();
    }

    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            timeStart = mediaPlayer.getCurrentPosition();
            seekBar.setProgress((int) timeStart);
            double timeRemaining = finalTime - timeStart;
            songDuration.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
            durationHandler.postDelayed(this, 100);
        }
    };
}
