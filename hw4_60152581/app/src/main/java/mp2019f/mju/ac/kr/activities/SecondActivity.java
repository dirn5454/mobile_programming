package mp2019f.mju.ac.kr.activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{



    private Integer num1;
    private Integer num2;
    private Integer num3;
    private Integer num4;

    Integer i =null;
     private int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);



        TextView textView = findViewById(R.id.putText);
        Button upButton = findViewById(R.id.upButton);
        Button downButton = findViewById(R.id.downButton);
        ImageView imageView = findViewById(R.id.imageView);

        upButton.setOnClickListener(this);
        downButton.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();



        if(extras != null){
            String name = extras.getString("이름");
            Integer age = extras.getInt("나이");
            String species = extras.getString("종");
            String personality = extras.getString("성격");





            i = extras.getInt("flag");

            if ( i == 0 ){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.simba));
                 num1 = extras.getInt("like");
                 num2 = extras.getInt("dislike");
            }
            else{
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.timon));
                 num3 = extras.getInt("like");
                 num4 = extras.getInt("dislike");
            }
            textView.setText("Name is " + name + " \n" +"age is " + age + " \n" +"species is " + species + " \n"+ "personality is " + personality + " \n");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = getIntent();
        switch (v.getId()){
            case R.id.upButton:
                if(i==0) {
                    count = getIntent().getIntExtra("like",num1);
                    intent.putExtra("returnData",count+1);
                    setResult(1,intent);
                    finish();
                    break;
                }
                else {
                    count = getIntent().getIntExtra("like",num3);
                    intent.putExtra("returnData",count+1);
                    setResult(3,intent);
                    finish();
                    break;
                }


            case R.id.downButton:
                if(i==0) {
                    count = getIntent().getIntExtra("dislike",num2);
                    intent.putExtra("returnData",count-1);
                    setResult(2,intent);
                    finish();
                    break;
                }
                else {
                    count = getIntent().getIntExtra("dislike", num4);
                    intent.putExtra("returnData", count - 1);
                    setResult(4, intent);
                    finish();
                    break;
                }

        }
    }
}
