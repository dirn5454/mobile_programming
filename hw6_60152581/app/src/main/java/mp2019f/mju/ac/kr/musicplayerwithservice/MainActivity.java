package mp2019f.mju.ac.kr.musicplayerwithservice;



import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView textView1;
    private TextView textView2;

    private ImageView img1,img2;

    private final int REQ = 2;

    int i = 0;
    int like1 = 0;
    int play1 = 0;
    int like2 = 0;
    int play2 = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        img1 = (ImageView)findViewById(R.id.oceanImage);
        img2 = (ImageView)findViewById(R.id.blurryImage);


        SharedPreferences sf =getSharedPreferences("sf", MODE_PRIVATE);
        SharedPreferences.Editor edit  = sf.edit();

        like1 = sf.getInt("like1",0);
        play1 = sf.getInt("play1",0);
        like2 = sf.getInt("like2",0);
        play2 = sf.getInt("play2",0);

        textView1.setText("가수 : Reddy 제목 : Ocean View \nPlay: "+play1 +"   Likes:  "+like1);
        textView2.setText("가수 : Beenzino 제목 : Blurry \nPlay: "+play2 +"   Likes:  "+like2);

        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.oceanImage:

                i = 0;

                Intent intent1 = new Intent(getApplicationContext(), SecondActivity.class);

                intent1.putExtra("i",i);
                intent1.putExtra("play1",play1);
                intent1.putExtra("like1",like1);
                startActivityForResult(intent1,REQ);
                break;


            case R.id.blurryImage:

                i = 1;

                Intent intent2 = new Intent(getApplicationContext(), SecondActivity.class);

                intent2.putExtra("i",i);
                intent2.putExtra("play2",play2);
                intent2.putExtra("like2",like2);
                startActivityForResult(intent2,REQ);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ){
            if(resultCode == 0){
                int i;
                i = data.getIntExtra("i",-1);
                if(i == 0){
                    play1 = data.getIntExtra("play",-1);
                    like1 = data.getIntExtra("like",-1);
                    textView1.setText("Play: "+play1 +"   Likes:  "+like1);
                }
                if(i == 1){
                    play2 = data.getIntExtra("play",-1);
                    like2 = data.getIntExtra("like",-1);
                    textView2.setText("Play: "+play2 +"   Likes:  "+like2);
                }

            }
        }
    }
}