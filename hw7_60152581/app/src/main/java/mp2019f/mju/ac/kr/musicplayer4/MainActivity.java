package mp2019f.mju.ac.kr.musicplayer4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button ovButton, bButton;
    private TextView ovText, bText;
    int temp = 0;
    int ovlike = 0;
    int ovplay = 0;
    int blike = 0;
    int bplay = 0;
    private final int REQ = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ovButton = findViewById(R.id.ovbutton);
        bButton = findViewById(R.id.bButton);
        ovText = findViewById(R.id.ovtext);
        bText = findViewById(R.id.btext);

        SharedPreferences sp =getSharedPreferences("sp", MODE_PRIVATE);

        ovButton.setOnClickListener(this);
        bButton.setOnClickListener(this);

        ovlike = sp.getInt("ovlike",0);
        ovplay = sp.getInt("ovplay",0);
        blike = sp.getInt("blike",0);
        bplay = sp.getInt("bplay",0);

        ovText.setText("Play: "+ovplay +"   Likes:  "+ovlike);
        bText.setText("Play: "+bplay +"   Likes:  "+blike);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ovbutton:

                temp = 0;

                Intent intent1 = new Intent(getApplicationContext(), SecondActivity.class);

                intent1.putExtra("temp",temp);
                intent1.putExtra("ovplay",ovplay);
                intent1.putExtra("ovlike",ovlike);
                startActivityForResult(intent1,REQ);
                break;


            case R.id.bButton:

                temp = 1;

                Intent intent2 = new Intent(getApplicationContext(), SecondActivity.class);

                intent2.putExtra("temp",temp);
                intent2.putExtra("bplay",bplay);
                intent2.putExtra("blike",blike);
                startActivityForResult(intent2,REQ);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ){
            if(resultCode == 0){
                int temp;
                temp = data.getIntExtra("temp",-1);
                if(temp == 0){
                    ovplay = data.getIntExtra("play",-1);
                    ovlike = data.getIntExtra("like",-1);
                    ovText.setText("Play: "+ovplay +"   Likes:  "+ovlike);
                }
                if(temp == 1){
                    bplay = data.getIntExtra("play",-1);
                    blike = data.getIntExtra("like",-1);
                    bText.setText("Play: "+bplay +"   Likes:  "+blike);
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}