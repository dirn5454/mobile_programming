package mp2019f.mju.ac.kr.tipcalculator;


import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    double sale;
    double tip;
    double total;
    int seekValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final RadioGroup radioGroup1 = findViewById(R.id.radioGroup1);
        final EditText saleText = findViewById(R.id.salesText);
        final EditText tipText = findViewById(R.id.tipText);
        final EditText totalText = findViewById(R.id.totalText);
        final SeekBar seekBar = findViewById(R.id.seekBar);
        final TextView rateText = findViewById(R.id.rateText);
        final CheckBox checkBox = findViewById(R.id.darkbox);
        final ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        final TextView salesWord = findViewById(R.id.sales);
        final TextView tipWord = findViewById(R.id.tip);
        final TextView totalWord = findViewById(R.id.total);
        final RadioButton notipButton = findViewById(R.id.notipButton);
        final RadioButton tipbyButton = findViewById(R.id.tipbyButton);
        final RadioButton maxtipButton = findViewById(R.id.maxtipButton);
        final RadioButton randtipButton = findViewById(R.id.randtipButton);
        final TextView s1 = findViewById(R.id.s1);
        final TextView s2 = findViewById(R.id.s2);
        final TextView s3 = findViewById(R.id.s3);
        final TextView rate = findViewById(R.id.rate);


        final Random rand = new Random();




        saleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    sale = Double.parseDouble(saleText.getText().toString().trim());
                }
                catch(Exception e){
                    sale =0;
                }
                finally{
                    tip = sale * seekValue/100;
                    total = sale + tip;
                    tipText.setText(String.valueOf(tip));
                    totalText.setText(String.valueOf(total));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, int progress, boolean fromUser) {
                rateText.setText(String.valueOf(progress)+"%");

                seekValue =  seekBar.getProgress();
                tip = sale * seekValue/100;
                total = sale + tip;
                tipText.setText(String.valueOf(tip));
                totalText.setText(String.valueOf(total));

                radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch(i) {
                            case R.id.notipButton:
                                seekBar.setEnabled(false);
                                seekValue=0;
                                tip = 0;
                                total = 0;
                                seekBar.setProgress(seekValue);
                                rateText.setText(String.valueOf(seekValue)+"%");
                                tipText.setText(String.valueOf(tip));
                                totalText.setText(String.valueOf(total));

                                break;
                            case R.id.randtipButton:
                                seekBar.setEnabled(true);
                                randtipButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int num = rand.nextInt(31);

                                        tip = sale * num/100;
                                        total = sale + tip;
                                        seekBar.setProgress(num);
                                        rateText.setText(String.valueOf(num)+"%");
                                        tipText.setText(String.valueOf(tip));

                                        totalText.setText(String.valueOf(total));
                                    }
                                });




                                break;

                            case R.id.tipbyButton:

                                seekBar.setEnabled(true);
                                seekValue =  seekBar.getProgress();

                                break;

                            case R.id.maxtipButton:
                                seekBar.setEnabled(false);
                                seekValue=30;
                                tip = sale * seekValue/100;
                                total = sale + tip;
                                seekBar.setProgress(seekValue);
                                rateText.setText(String.valueOf(seekValue)+"%");
                                tipText.setText(String.valueOf(tip));
                                totalText.setText(String.valueOf(total));


                                break;
                        }
                    }
                });


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        checkBox.setOnClickListener(new CheckBox.OnClickListener() {
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    constraintLayout.setBackgroundColor(Color.DKGRAY);
                    tipbyButton.setTextColor(Color.WHITE);
                    maxtipButton.setTextColor(Color.WHITE);
                    randtipButton.setTextColor(Color.WHITE);
                    notipButton.setTextColor(Color.WHITE);
                    s1.setTextColor(Color.WHITE);
                    s2.setTextColor(Color.WHITE);
                    s3.setTextColor(Color.WHITE);
                    salesWord.setTextColor(Color.WHITE);
                    tipWord.setTextColor(Color.WHITE);
                    totalWord.setTextColor(Color.WHITE);
                    rate.setTextColor(Color.WHITE);
                    checkBox.setTextColor(Color.WHITE);
                    radioGroup1.setBackgroundColor(Color.DKGRAY);
                }
                else{
                    constraintLayout.setBackgroundColor(Color.WHITE);
                    tipbyButton.setTextColor(Color.DKGRAY);
                    maxtipButton.setTextColor(Color.DKGRAY);
                    randtipButton.setTextColor(Color.DKGRAY);
                    notipButton.setTextColor(Color.DKGRAY);
                    s1.setTextColor(Color.DKGRAY);
                    s2.setTextColor(Color.DKGRAY);
                    s3.setTextColor(Color.DKGRAY);
                    salesWord.setTextColor(Color.DKGRAY);
                    tipWord.setTextColor(Color.DKGRAY);
                    totalWord.setTextColor(Color.DKGRAY);
                    rate.setTextColor(Color.DKGRAY);
                    checkBox.setTextColor(Color.DKGRAY);
                    radioGroup1.setBackgroundColor(Color.WHITE);
                }
            }
        });
    }
}
