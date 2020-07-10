package mp2019f.mju.ac.kr.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{



    private Integer num1;
    private Integer num2;
    private Integer num3;
    private Integer num4;
    private MediaPlayer mp1;
    private MediaPlayer mp2;
    private TextView startTime;
    private TextView endTime;
    private SeekBar seekBar;

    private int pausePosition =0;
    private SimpleDateFormat df;


    Integer i =null;
    private int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mp1 = MediaPlayer.create(getApplicationContext(),R.raw.oceanview);
        mp2 = MediaPlayer.create(getApplicationContext(),R.raw.blurry);
        seekBar = findViewById(R.id.seekBar);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        df = new SimpleDateFormat("mm:ss");


        TextView likeText = findViewById(R.id.likeText);
        TextView textView = findViewById(R.id.putText);
        Button playButton = findViewById(R.id.playButton);
        Button stopButton = findViewById(R.id.stopButton);
        Button upButton = findViewById(R.id.upButton);
        ImageView imageView = findViewById(R.id.imageView);


        upButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);

        final Handler mHandler = new Handler();

        SecondActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mp1 != null && mp1.isPlaying()){
                    seekBar.setProgress(mp1.getCurrentPosition());
                    int a = seekBar.getProgress();
                    startTime.setText(String.valueOf(df.format(new Date(a))));

                    endTime.setText(df.format(new Date(mp1.getDuration() - seekBar.getProgress())));

                }
                else if(mp2 != null && mp2.isPlaying()){
                    seekBar.setProgress(mp2.getCurrentPosition());
                    int a = seekBar.getProgress();
                    startTime.setText(String.valueOf(df.format(new Date(a))));
                    endTime.setText(df.format(new Date(mp2.getDuration() - seekBar.getProgress())));
                }
                mHandler.postDelayed(this,1000);
            }
        });

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            String Artist = extras.getString("Artist");
            String Title = extras.getString("Title");


            i = extras.getInt("flag");

            if ( i == 0 ){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.oceanview));
                num1 = extras.getInt("play");
                num2 = extras.getInt("like");
                seekBar.setMax(mp1.getDuration());
                likeText.setText("Like: "+num2);
                startTime.setText(df.format(seekBar.getProgress()));
                endTime.setText(df.format(mp1.getDuration()));

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(final SeekBar seekBar, int p, boolean b) {
                        if(b && mp1 != null){
                            mp1.seekTo(p);



                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });


            }
            else{
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.blurry));
                num3 = extras.getInt("play");
                num4 = extras.getInt("like");
                seekBar.setMax(mp2.getDuration());
                likeText.setText("Like: "+num4);
                startTime.setText(df.format(seekBar.getProgress()));
                endTime.setText(df.format(mp2.getDuration()));

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int p, boolean b) {
                        if(mp2 != null){
                            mp2.seekTo(p);


                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });



            }
            textView.setText("Artist: " + Artist + " \n" +"Title: " + Title  );
        }
    }




    @Override
    public void onClick(View v) {
        Intent intent = getIntent();
        switch (v.getId()){
            case R.id.upButton:
                if(i==0) {
                    count = getIntent().getIntExtra("like",num2);
                    intent.putExtra("returnData",count+1);
                    setResult(1,intent);
                    //finish()를 지우면 첫번째 화면으로 안넘어감
                    break;
                }
                else {
                    count = getIntent().getIntExtra("like",num4);
                    intent.putExtra("returnData",count+1);
                    setResult(2,intent);

                    break;
                }
            case R.id.playButton:
                if(i==0) {

                    if (!mp1.isPlaying()){
                        mp1.seekTo(pausePosition);
                        mp1.start();

                    }
                    else{
                        pausePosition = mp1.getCurrentPosition();
                        mp1.pause();
                    }



     /*               count = getIntent().getIntExtra("like",num1);
                    intent.putExtra("returnData",count+1);
                    setResult(3,intent);
                    */

                    break;
                }
                else {
                    if (!mp2.isPlaying()){
                        mp2.seekTo(pausePosition);
                        mp2.start();
                    }
                    else{
                        pausePosition = mp2.getCurrentPosition();
                        mp2.pause();
                    }


                    /*
                    count = getIntent().getIntExtra("like",num3);
                    intent.putExtra("returnData",count+1);
                    setResult(4,intent);
                    */
                    break;
                }


            case R.id.stopButton:
                if(i==0){
                    mp1.pause();
                    mp1.seekTo(0);
                    seekBar.setProgress(0);
                    startTime.setText(df.format(new Date(0)));

                }
                else{
                    mp2.pause();
                    mp2.seekTo(0);
                    seekBar.setProgress(0);
                    startTime.setText(df.format(new Date(0)));

                }

        }
    }
}