# MediaControl


                                                           Media Control
Dhyey Farasram
N01269001

Introduction:

•	Media Controller is a view that contains buttons like "Play/Pause", "Rewind", "Fast Forward" and a progress slider.

•	The Media Controller helps us to set the controls and put them in a window floating above your application.

•	To able to use media control it is required to add its package and to initialize it.

•	 Functions such as start, stop, pause, hide(), show() are some of the main functions that media control uses.

•	The list of functions callback I used in my code to reach desire goal:

	o	getCurrentPosition() : Gets the current time for the media that is playing.
	
	o	getDuration(): Get the time duration for the media that is  playing.
	
	o	isPlaying(): It checks if the media is playing or not
	
	o	seekTo(): Seeks to the time 
	
	o	setDataSource(): Sets the data(media) into the video/media view.
	
	o	setProgress(): Sets the progress for seek bar
	
	o	postDelay(): Delays the time.
	
	o	setAnchorView() : Set the view that acts as the anchor for the control view.
	
	o	MediaPlayer.create(): This will allow us to set the context for media-player.
	
	o	SetMediaController(): Sets the floating controls for the media player.
	
	o	setVideoURI(): Sets the path for the media player.
	
•	For each function start(), stop(), pause(), forward(), backward(), they have their respective buttons configured in xml file.



History:

•	Media Control was introduced in API level 1

•	Its main package was java.lang.object and it has sub packages android.view.View -> android.view.ViewGroup -> adroid.wifget.FrameLayout -> android.widget.MediaController.


Methods:

I implemented following methods to my code

•	Pause(): It pauses the playback of media.

•	Play(): It will get the media start playing and it will also get the current time for the media that is playing and sets the time into the seek bar.

•	Stop(): It will stop the play back of media.

•	Forward(): It will skip the time 5 seconds forward only if the current time and these 5 seconds don’t exceed the final(end of the media) time. And it will set this time in seek bar.

•	Backward(): It will skip the time 5 seconds backward only if the current time and these 5 seconds exceed the start(beginning of the media) time. And it will set this time in seek bar.

•	UpdateSeekBarTime(): This is a function updates the time for the seek bar as the name says itself. It will set the time in a format that is minutes and then seconds. It will update the time every 100 milli-seconds.


My Demo App:

Following is the way how to set up the controller and how to use it.

•	First thing to do is initiate the media controller, this is how I did in my code MediaPlayer mediaPlayer.

•	Then next step is to give media to the controller so it can play, this is how I did mediaPlayer = MediaPlayer.create(context: this, R.raw.music);

•	To play video we need to create video view, this is how it’s done

	o	VideoView simpleVideoView = (VideoView) findViewById();

•	To able to play music/video from online source we need to give the internet permission to the application

	o	<uses-permission android:name=”android.permissoin.INTERNET”/>
	
•	There are two ways to implement media control

	o	First: Make a layout file for the controls and make their respective methods and call them on onClickEvent().
	
	o	Second: Uses setAnchorView() this will allow us to use the floating controls for the media player. By using this in our code we wont have to make extra methods such as forward() and backward(), because it has already built-	in.
	
•The last thing to do is to let the media get started, to do so we will use .start() method(in-built).

Code:

•	Below is the code with comments 

					        MainActivity.java
//Dhyey Farasram
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
    
    public void pause(View view) {
        mediaPlayer.pause(); //pauses the media playback
    }

    public void play(View view) {
        mediaPlayer.start(); //starts the media playback
        timeStart = mediaPlayer.getCurrentPosition(); //gets the current time of media and stores in timeStart
        seekBar.setProgress((int) timeStart); //sets the seek bar 
        durationHandler.postDelayed(updateSeekBarTime, 100);
    }
    public void forward(View view) { // this function will allow us to skip the time forward for 5 seconds
        if ((timeStart + forwardTime) <= 0) {
            timeStart = mediaPlayer.getCurrentPosition() + forwardTime;
            mediaPlayer.seekTo((int) timeStart);
        }
    }

    public void backforward(View view) { // this function will allow us to skip the time backward for 5 seconds
        if ((timeStart - backwardTime) > 0) {
            timeStart = mediaPlayer.getCurrentPosition() - backwardTime;
            mediaPlayer.seekTo((int) timeStart);
        }
    }

    public void stop(View view) {

        mediaPlayer.stop(); //stops the media playback
    }

    private Runnable updateSeekBarTime = new Runnable() { //this function will keep on updating the time for seekbar
        public void run() {
            timeStart = mediaPlayer.getCurrentPosition();
            seekBar.setProgress((int) timeStart);
            double timeRemaining = finalTime - timeStart;
            songDuration.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
            durationHandler.postDelayed(this, 100);
        }
    };
}

                                                  VideoPlayer.java

public class  VideoPlayer extends AppCompatActivity {

    MediaController mediaControls; //declaration of mediacontrol

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);



        VideoView simpleVideoView = (VideoView) findViewById(R.id.videoView2); 
        //finds the view by id and sets into simplevdeioview

        if (mediaControls == null) {
            // create an object of media controller class
            mediaControls = new MediaController(VideoPlayer.this);
            mediaControls.setAnchorView(simpleVideoView);
        }
        // set the media controller for video view
        simpleVideoView.setMediaController(mediaControls);

        simpleVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));
        //sets path for media player and let player which file to play

        simpleVideoView.start(); // start a video

    }
    
Reference:

•	https://developer.android.com/reference/android/widget/MediaController

•	https://abhiandroid.com/ui/videoview

•	https://www.tutorialspoint.com/android/android_mediaplayer.htm

