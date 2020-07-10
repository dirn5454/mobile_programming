package mp2019f.mju.ac.kr.musicplayerwithservice;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    private static Intent ServiceIntent;
    private ImageView imageView;
    private MediaPlayer mp;
    private SimpleDateFormat df;
    private SeekBar seekbar;
    private TextView startTime;
    private TextView endTime;
    private TextView infotext;
    private TextView likeText;
    private ImageButton goodbutton;
    private ImageButton playbutton;
    private ImageButton stopbutton;

    int play = 0;
    int like = 0;
    int checkplay = 0;
    int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final Handler mhandler = new Handler();
        SharedPreferences sf = getSharedPreferences("sf", MODE_PRIVATE);

        imageView =  findViewById(R.id.imageView);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);

        likeText = findViewById(R.id.likeText);
        infotext =  findViewById(R.id.putText);
        goodbutton =  findViewById(R.id.upButton);
        playbutton =  findViewById(R.id.playButton);
        stopbutton =  findViewById(R.id.stopButton);
        seekbar =  findViewById(R.id.seekBar);

        Bundle extras = getIntent().getExtras();
        df = new SimpleDateFormat("mm:ss");

        goodbutton.setOnClickListener(this);
        playbutton.setOnClickListener(this);
        stopbutton.setOnClickListener(this);

        startTime.setText(df.format(new Date(0)));


        if (extras != null) {
            i = getIntent().getIntExtra("i", 0);

            if (i == 0) {

                imageView.setImageResource(R.drawable.oceanview);
                infotext.setText("가수: Reddy\n제목: Ocean View");
                like = extras.getInt("like1");
                play = extras.getInt("play1");
                likeText.setText("Likes  :" + like);

                mp = MediaPlayer.create(getApplicationContext(), R.raw.oceanview);
                seekbar.setMax(mp.getDuration());

                mp.seekTo(sf.getInt("time1", 0));
                seekbar.setProgress(mp.getCurrentPosition());
                startTime.setText(df.format(seekbar.getProgress()));
                endTime.setText(df.format(mp.getDuration()));
            }

            if (i == 1) {

                imageView.setImageResource(R.drawable.blurry);
                infotext.setText("Artist: Beenzino \n Blurry");
                like = extras.getInt("like2");
                play = extras.getInt("play2");
                likeText.setText("Likes  :" + like);

                mp = MediaPlayer.create(getApplicationContext(), R.raw.blurry);

                seekbar.setMax(mp.getDuration());

                mp.seekTo(sf.getInt("time2", 0));
                seekbar.setProgress(mp.getCurrentPosition());
                startTime.setText(df.format(seekbar.getProgress()));
                endTime.setText(df.format(mp.getDuration()));
            }


            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser && mp != null) {
                        mp.seekTo(progress);
                        startTime.setText(df.format(new Date(progress)));
                    }
                }

                @Override
                public void onStartTrackingTouch(android.widget.SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(android.widget.SeekBar seekBar) {

                }
            });

            SecondActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mp != null && mp.isPlaying()) {
                        seekbar.setProgress(mp.getCurrentPosition());
                        startTime.setText(df.format(new Date(seekbar.getProgress())));
                        endTime.setText(df.format(new Date(mp.getDuration() - seekbar.getProgress())));
                        mp.setLooping(false);
                    }
                    mhandler.postDelayed(this, 100);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playButton:

                if (mp == null)
                    return;
                if (mp.isPlaying()) {
                 //   mp.pause();

                } else {
                //    mp.start();

                    checkplay++;
                    if (checkplay == 1) {
                        play += 1;
                    }
                    ServiceIntent = new Intent(this, MyIntentService.class );
                    ServiceIntent.putExtra("i",i);
                    startService(ServiceIntent);
                }
                break;

            case R.id.stopButton:

                if (mp == null)
                    return;

                mp.pause();
                mp.seekTo(0);
                seekbar.setProgress(0);
                startTime.setText(df.format(new Date(0)));

                break;

            case R.id.upButton:
                like += 1;
                likeText.setText("Likes  :" + like);
                break;
        }

    }


    @Override
    public void onBackPressed() {
        Intent returnIntent = getIntent();
        SharedPreferences sf = getSharedPreferences("sf", MODE_PRIVATE);
        SharedPreferences.Editor edit = sf.edit();

        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }

        returnIntent.putExtra("i", i);
        returnIntent.putExtra("play", play);
        returnIntent.putExtra("like", like);


        if (i == 0) {
            edit.putInt("play1", play);
            edit.putInt("like1", like);
            edit.putInt("time1", seekbar.getProgress());
        }
        if (i == 1) {
            edit.putInt("play2", play);
            edit.putInt("like2", like);
            edit.putInt("time2", seekbar.getProgress());
        }
        edit.apply();

        setResult(0, returnIntent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        SharedPreferences sf = getSharedPreferences("sf", MODE_PRIVATE);
        SharedPreferences.Editor edit = sf.edit();


        if (i == 0) {
            edit.putInt("gplay1", play);
            edit.putInt("glike1", like);
            edit.putInt("gtime1", seekbar.getProgress());
        }
        if (i == 1) {
            edit.putInt("play2", play);
            edit.putInt("like2", like);
            edit.putInt("time2", seekbar.getProgress());
        }
        edit.apply();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }
}