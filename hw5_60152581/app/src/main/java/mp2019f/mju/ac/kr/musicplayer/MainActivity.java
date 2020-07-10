package mp2019f.mju.ac.kr.musicplayer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final int REQUEST_CODE = 2;

    int num1 = 0;
    int num2 = 2;
    int num3 = 0;
    int num4 = 55;





    TextView playNum1;
    TextView likeNum1;
    TextView playNum2;
    TextView likeNum2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        playNum1 = findViewById(R.id.playNum1);
        likeNum1 = findViewById(R.id.likeNum1);
        playNum2 = findViewById(R.id.playNum2);
        likeNum2 = findViewById(R.id.likeNum2);

        ImageView oceanImage = findViewById(R.id.oceanImage);
        ImageView blurryImage = findViewById(R.id.blurryImage);

        oceanImage.setOnClickListener(this);
        blurryImage.setOnClickListener(this);

        playNum1.setText(""+ num1);
        likeNum1.setText(""+ num2);
        playNum2.setText(""+ num3);
        likeNum2.setText(""+ num4);


    }

    int i;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.oceanImage:
                Intent intent1 = new Intent(MainActivity.this, SecondActivity.class);

                intent1.putExtra("Artist", "Reddy");
                intent1.putExtra("Title", "Ocean View");


                intent1.putExtra("play", num1);
                intent1.putExtra("like", num2);

                i=0;
                intent1.putExtra("flag", i);

                startActivityForResult(intent1, REQUEST_CODE);
                break;

            case R.id.blurryImage:

                Intent intent2 = new Intent(MainActivity.this, SecondActivity.class);
                intent2.putExtra("Artist", "Beenzino");
                intent2.putExtra("Title", "Blurry");

                intent2.putExtra("play", num3);
                intent2.putExtra("like", num4);

                i=1;
                intent2.putExtra("flag", i);


                startActivityForResult(intent2, REQUEST_CODE);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == 1) {
                num2 = data.getIntExtra("returnData", -1);
                likeNum1.setText(String.valueOf(num2));

            }
            if (resultCode == 2) {
                num4 = data.getIntExtra("returnData", -1);
                likeNum2.setText(String.valueOf(num4));

            }
        }
    }
}
