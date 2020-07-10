package mp2019f.mju.ac.kr.musicplayer4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView ImageView;
    private Button playButton, pauseButton, upButton;
    private TextView mpText, startTime, endTime, likeText;
    private SeekBar SeekBar;
    private SimpleDateFormat df;
    private MyService Bservice;
    public static SecondActivity activity = null;
    boolean isService = false;
    int pcount= 0;
    int upcount = 0;
    int temp = 0;
    int playcheck = 0;
    Handler mHandler = new Handler();

    public void StartBindService(){
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        SharedPreferences sp =getSharedPreferences("sp", MODE_PRIVATE);
        intent.putExtra("temp",temp);
        intent.putExtra("ovtime",sp.getInt("ovtime",0));
        intent.putExtra("btime",sp.getInt("btime",0));
        startService(intent);
        bindService(intent,Con,BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        activity = this;
        mpText = findViewById(R.id.mpText);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        likeText = findViewById(R.id.likeText);
        SeekBar = findViewById(R.id.seekBar);
        Bundle extras = getIntent().getExtras();
        df = new SimpleDateFormat("mm:ss");
        ImageView = findViewById(R.id.imageView);
        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        upButton = findViewById(R.id.upButton);
        startTime.setText(df.format(new Date(0)));
        playButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        upButton.setOnClickListener(this);
        temp = getIntent().getIntExtra("temp",0);
        StartBindService();

        if(extras != null){
            if(temp == 0){
                ImageView.setImageResource(R.drawable.oceanview);
                mpText.setText("Artist: 레디\nTitle: 오션 뷰");
                upcount = extras.getInt("ovlike");
                pcount = extras.getInt("ovplay");
                likeText.setText("Likes  :"+upcount);
            }
            if(temp == 1){
                ImageView.setImageResource(R.drawable.blurry);
                mpText.setText("Artist: 빈지노\nTitle: Blurry");
                upcount = extras.getInt("blike");
                pcount = extras.getInt("bplay");
                likeText.setText("Likes  :"+upcount);

            }
        }
    }
    private ServiceConnection Con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Intent intent = getIntent();
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            Bservice = binder.getService();

            if(Bservice.whatplay() == temp && Bservice.playing()){
                playButton.setBackgroundResource(R.drawable.pause);
            }
            if(Bservice.whatplay() == temp){
                seekBarOnProgress();
            }
            if(Bservice.whatplay()  != temp&& !Bservice.playing()){
                Bservice.ServiceStop();
                stopService(intent);
                unbindService(Con);
                StartBindService();
            }
            if(Bservice.whatplay()  != temp){
                Bservice.ServiceStop();
                stopService(intent);
                unbindService(Con);
                StartBindService();
            }
            isService = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isService = false;
        }
    };


    private void createNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
        Intent intent = getIntent();
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        if(temp == 0){
            builder.setContentText("오션 뷰");
        }
        else
            builder.setContentText("Blurry");
        builder.setContentTitle("MediaPlayer");

        builder.setContentIntent(pi);
        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            assert notificationManager != null;
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        assert notificationManager != null;
        notificationManager.notify(1, builder.build());

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.playButton:
                if(Bservice.mp == null){
                    return;
                }
                if(Bservice.whatplay() == temp && Bservice.playing()){
                    playButton.setBackgroundResource(R.drawable.play);
                    Bservice.stop();
                }
                else{
                    if(Bservice.whatplay() != temp ){
                        Bservice.mp.reset();
                        Bservice.ServiceStop();
                        Intent intent = getIntent();
                        stopService(intent);
                        unbindService(Con);
                        StartBindService();
                    }
                    playButton.setBackgroundResource(R.drawable.pause);
                    playcheck++;
                    if(playcheck == 1){
                        pcount +=1;
                    }
                    Bservice.start();
                }
                break;
            case R.id.pauseButton:
                if(Bservice.isRunning == false)
                    return;
                Bservice.pause();
                playButton.setBackgroundResource(R.drawable.play);
                Bservice.move(0);
                SeekBar.setProgress(0);
                startTime.setText(df.format(new Date(0)));
                break;
            case R.id.upButton:
                upcount += 1;
                likeText.setText("Likes  :"+upcount);
                break;
        }
    }
    private void seekBarOnProgress(){
        startTime.setText(df.format(new Date(0)));
        SeekBar.setMax(Bservice.getDuration());
        SeekBar.setProgress(Bservice.getCurrentPosition());
        startTime.setText(df.format(SeekBar.getProgress()));
        endTime.setText(df.format(Bservice.getDuration()));

        SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && Bservice.mp != null) {
                    Bservice.move(progress);
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
                if (Bservice.mp != null && Bservice.playing()) {
                    SeekBar.setProgress(Bservice.getCurrentPosition());
                    startTime.setText(df.format(new Date(SeekBar.getProgress())));
                    endTime.setText(df.format(new Date(Bservice.getDuration() - SeekBar.getProgress())));
                }
                assert Bservice.mp != null;
                Bservice.mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        createNotification();

                    }
                });
                mHandler.postDelayed(this, 100);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = getIntent();

        returnIntent.putExtra("temp",temp);
        returnIntent.putExtra("play",pcount);
        returnIntent.putExtra("like",upcount);

        SharedPreferences sp =getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor edit  = sp.edit();
        if(temp == 0){
            edit.putInt("ovplay",pcount);
            edit.putInt("ovlike",upcount);
            edit.putInt("ovtime",SeekBar.getProgress());
        }
        if(temp == 1){
            edit.putInt("bplay",pcount);
            edit.putInt("blike",upcount);
            edit.putInt("btime",SeekBar.getProgress());
        }
        edit.apply();
        setResult(0,returnIntent);
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        SharedPreferences sp =getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor edit  = sp.edit();

        if(temp == 0){
            edit.putInt("ovplay",pcount);
            edit.putInt("ovlike",upcount);
            edit.putInt("ovtime",SeekBar.getProgress());
        }
        if(temp == 1){
            edit.putInt("bplay",pcount);
            edit.putInt("blike",upcount);
            edit.putInt("btime",SeekBar.getProgress());
        }
        edit.apply();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unbindService(Con);
        super.onDestroy();
    }
}
