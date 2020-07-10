package mp2019f.mju.ac.kr.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    private final int REQUEST_CODE = 2;

    int num1 = 12000;
    int num2 = 2;
    int num3 = 58;
    int num4 = 55;




    TextView simbaLike;
    TextView simbaDislike;
    TextView timonLike;
    TextView timonDislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        simbaLike = findViewById(R.id.simbaLike);
        simbaDislike = findViewById(R.id.simbaDislike);
        timonLike = findViewById(R.id.timonLike);
        timonDislike = findViewById(R.id.timonDislike);

        Button  simbaButton = findViewById(R.id.simbaButton);
        Button  timonButton = findViewById(R.id.timonButton);

        simbaButton.setOnClickListener(this);
        timonButton.setOnClickListener(this);

        simbaLike.setText(""+ num1);
        simbaDislike.setText(""+ num2);
        timonLike.setText(""+ num3);
        timonDislike.setText(""+ num4);


    }

    int i;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.simbaButton:
            Intent intent1 = new Intent(FirstActivity.this, SecondActivity.class);

            intent1.putExtra("이름", "Simba");
            intent1.putExtra("나이", 2);
            intent1.putExtra("종", "Lien");
            intent1.putExtra("성격", "Brave");

            intent1.putExtra("like", num1);
            intent1.putExtra("dislike", num2);

            i=0;
            intent1.putExtra("flag", i);

            startActivityForResult(intent1, REQUEST_CODE);
            break;

            case R.id.timonButton:

            Intent intent2 = new Intent(FirstActivity.this, SecondActivity.class);
            intent2.putExtra("이름", "Timon");
            intent2.putExtra("나이", 15);
            intent2.putExtra("종", "Meerkat");
            intent2.putExtra("성격", "Shovel");

            intent2.putExtra("like", num3);
            intent2.putExtra("dislike", num4);

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
                num1 = data.getIntExtra("returnData", -1);
                simbaLike.setText(String.valueOf(num1));
            }
            if (resultCode == 2) {
                num2 = data.getIntExtra("returnData", -1);
                simbaDislike.setText(String.valueOf(num2));
            }
            if (resultCode == 3) {
                num3 = data.getIntExtra("returnData", -1);
                timonLike.setText(String.valueOf(num3));
            }
            if (resultCode == 4) {
                num4 = data.getIntExtra("returnData", -1);
                timonDislike.setText(String.valueOf(num4));
            }


        }
    }
}
