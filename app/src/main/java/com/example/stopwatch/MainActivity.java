package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //TextView timeView;
    //TextView timeView2;
    //TextView timeView3;
    TextView seekBarText;
    Button button;
    Button buttonStopSlot;
    SeekBar seekBarSpeed;
    Drawable cherry;
    Drawable grape;
    Drawable pear;
    Drawable strawberry;
    ImageView slotImage;
    ImageView slotImage2;
    ImageView slotImage3;

    int time;
    int time2;
    int time3;
    int speed;
    int i = 0;
    String counting = "Up";

    boolean stopSlotCheck1 = false;
    boolean stopSlotCheck2 = false;
    boolean stopSlotCheck3 = false;
    boolean slotMachineActive = false;

    CountEvent event;
    Handler handler;

    List initialSlotNums = new ArrayList<Integer>();

    Random r = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //timeView = findViewById(R.id.timeView);
        //timeView2 = findViewById(R.id.timeView2);
        //timeView3 = findViewById(R.id.timeView3);
        button = findViewById(R.id.button);
        buttonStopSlot = findViewById(R.id.stopSlotButton);
        seekBarSpeed = findViewById(R.id.seekBarSpeed);
        seekBarText = findViewById(R.id.seekBarText);
        cherry = getDrawable(R.drawable.cherry);
        grape = getDrawable(R.drawable.grape);
        pear = getDrawable(R.drawable.pear);
        strawberry = getDrawable(R.drawable.strawberry);
        slotImage = findViewById(R.id.slotImage2);
        slotImage2 = findViewById(R.id.slotImage);
        slotImage3 = findViewById(R.id.slotImage3);

        time = 0;
        time2 = 1;
        time3 = 2;
        speed = 100;

        event = new CountEvent();
        handler = new Handler();

        if (savedInstanceState != null) {
            time = savedInstanceState.getInt("time");
            time2 = savedInstanceState.getInt("time2");
            time3 = savedInstanceState.getInt("time3");
            //timeView.setText(time+"");
            //timeView2.setText(time2+"");
            //timeView3.setText(time3+"");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // This lets the chosen slot numbers to be random, also prevents duplicates so each slot is a different number
                initialSlotNums.add(0, 0);
                initialSlotNums.add(1, 1);
                initialSlotNums.add(2, 2);
                initialSlotNums.add(3, 3);

                boolean timeBool1 = false;
                boolean timeBool2 = false;

                time = Integer.parseInt(initialSlotNums.get(r.nextInt(4 - 0)).toString());

                while(timeBool1 == false) {
                    time2 = Integer.parseInt(initialSlotNums.get(r.nextInt(4 - 0)).toString());
                    if(time2 != time) {
                        timeBool1 = true;
                    }
                }

                while(timeBool2 == false) {
                    time3 = Integer.parseInt(initialSlotNums.get(r.nextInt(4 - 0)).toString());
                    if(time3 != time && time3 != time2) {
                        timeBool2 = true;
                    }
                }

                if (button.getText().equals("Start")) {
                    handler.postDelayed(event, speed);
                    button.setText("Reset");

                    stopSlotCheck1 = false;
                    stopSlotCheck2 = false;
                    stopSlotCheck3 = false;
                    slotMachineActive = true;
                    i = 0;
                }
                else {
                    handler.removeCallbacks(event);
                    //timeView.setText(0 + "");
                    //timeView2.setText(0 + "");
                    //timeView3.setText(0 + "");
                    slotImage.setImageDrawable(cherry);
                    slotImage2.setImageDrawable(cherry);
                    slotImage3.setImageDrawable(cherry);
                    time = 0;
                    time2 = 0;
                    time3 = 0;
                    i = 0;
                    button.setText("Start");
                    buttonStopSlot.setText("Stop first slot");
                }
            }
        });

        buttonStopSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(slotMachineActive == true) {
                    if(i == 0) {
                        stopSlotCheck2 = true;
                        buttonStopSlot.setText("Stop second slot");
                    }
                    else if(i == 1) {
                        stopSlotCheck1 = true;
                        buttonStopSlot.setText("Stop third slot");
                    }
                    else {
                        stopSlotCheck3 = true;
                        buttonStopSlot.setText("Stop first slot");

                        if(time == time2 && time == time3 && slotMachineActive == true) {
                            // Message saying player won
                            Toast t = Toast.makeText(getApplicationContext(), "JACKPOT!!!", Toast.LENGTH_SHORT);
                            t.show();
                        }
                        else {
                            // Message saying player did not win
                            Toast t = Toast.makeText(getApplicationContext(), "No jackpot.", Toast.LENGTH_SHORT);
                            t.show();
                        }
                        slotMachineActive = false;
                    }
                    i++;
                }
            }
        });

        seekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarText.setText(seekBarSpeed.getProgress() + "");
                speed = 1000 - seekBarSpeed.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private class CountEvent implements Runnable {

        @Override
        public void run() {
            if(counting.equals("Up")) {
                if(stopSlotCheck1 == false) {
                    if(time == 1) {
                        slotImage2.setImageDrawable(cherry);
                    }
                    else if(time == 2) {
                        slotImage2.setImageDrawable(grape);
                    }
                    else if(time == 3) {
                        slotImage2.setImageDrawable(pear);
                    }
                    else {
                        slotImage2.setImageDrawable(strawberry);
                    }
                    time++;
                }
                if(stopSlotCheck2 == false) {
                    if(time2 == 1) {
                        slotImage.setImageDrawable(cherry);
                    }
                    else if(time2 == 2) {
                        slotImage.setImageDrawable(grape);
                    }
                    else if(time2 == 3) {
                        slotImage.setImageDrawable(pear);
                    }
                    else {
                        slotImage.setImageDrawable(strawberry);
                    }
                    time2++;
                }
                if(stopSlotCheck3 == false) {
                    if(time3 == 1) {
                        slotImage3.setImageDrawable(cherry);
                    }
                    else if(time3 == 2) {
                        slotImage3.setImageDrawable(grape);
                    }
                    else if(time3 == 3) {
                        slotImage3.setImageDrawable(pear);
                    }
                    else {
                        slotImage3.setImageDrawable(strawberry);
                    }
                    time3++;
                }
            }

            //timeView.setText(time + "");
            //timeView2.setText(time2 + "");
            //timeView3.setText(time3 + "");
            handler.postDelayed(event, speed);

            if(stopSlotCheck1 == false) {
                if(time > 3) {
                    time = 0;
                }
            }
            if(stopSlotCheck2 == false) {
                if(time2 > 3) {
                    time2 = 0;
                }
            }
            if(stopSlotCheck3 == false) {
                if(time3 > 3) {
                    time3 = 0;
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putInt("time", time);
        super.onSaveInstanceState(saveInstanceState);
    }
}