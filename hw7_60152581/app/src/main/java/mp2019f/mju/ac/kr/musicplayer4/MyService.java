package mp2019f.mju.ac.kr.musicplayer4;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.widget.Toast;


public class MyService extends Service {
    MediaPlayer mp;
    int temp = 0;
    int Duration = 0;


    private final IBinder mBinder = new LocalBinder();
    boolean isRunning = false;

    public class  LocalBinder extends Binder {
        public MyService getService(){
            isRunning = true;
            return MyService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        if(isRunning == false) {
            temp = intent.getIntExtra("temp", 0);
            int btime = intent.getIntExtra("btime", 0);
            int ovtime = intent.getIntExtra("ovtime",0);
            if (temp == 0) {
                mp = MediaPlayer.create(getApplicationContext(), R.raw.oceanview);
                mp.seekTo(ovtime);

            } else{
                mp = MediaPlayer.create(getApplicationContext(), R.raw.blurry);
                mp.seekTo(btime);
            }

            Duration = mp.getDuration();
        }
        return mBinder;
    }

    public void start(){
        mp.start();
    }

    public void pause(){
        mp.pause();
        mp.seekTo(0);
    }
    public void stop(){
        mp.pause();
    }

    public boolean playing(){
        return mp.isPlaying();
    }

    public int getCurrentPosition(){
        return mp.getCurrentPosition();
    }
    public int getDuration(){
        return Duration;

    }
    public int whatplay(){
        return temp;
    }
    public void move(int i){
        mp.seekTo(i);
    }
    public void ServiceStop(){
        mp.stop();
        mp.reset();
        stopSelf();
        onDestroy();
    }

    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);

        registerReceiver(mBRBattery, filter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    BroadcastReceiver mBRBattery = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            assert action != null;
            if (action.equals(Intent.ACTION_BATTERY_LOW)) {
                Toast.makeText(context, "배터리가 부족합니다", Toast.LENGTH_LONG).show();
                if (mp != null) {
                    mp.pause();
                }
            }
            if (action.equals(Intent.ACTION_BATTERY_OKAY)) {
                Toast.makeText(context, "배터리가 충분합니다", Toast.LENGTH_LONG).show();
                if (mp != null) {
                    mp.start();
                }
            }
            if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
                Toast.makeText(context, "배터리 충전 중", Toast.LENGTH_LONG).show();
            }
            if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                Toast.makeText(context, "배터리 충전 종료", Toast.LENGTH_LONG).show();
            }
        }
    };
}
