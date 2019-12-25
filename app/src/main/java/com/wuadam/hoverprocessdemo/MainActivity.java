package com.wuadam.hoverprocessdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;

import com.wuadam.hoverprocess.HoverProcessView;

public class MainActivity extends AppCompatActivity {
    private HoverProcessView process;
    private SeekBar seek;
    private CheckBox cbAnimate;
    private Button btnInfiniteStart;
    private Button btnInfiniteStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        process = findViewById(R.id.process);
        seek = findViewById(R.id.seek);
        cbAnimate = findViewById(R.id.cb_animate);
        btnInfiniteStart = findViewById(R.id.btn_infinite_start);
        btnInfiniteStop = findViewById(R.id.btn_infinite_stop);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                process.setProgress(i, cbAnimate.isChecked());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == btnInfiniteStart) {
                    process.startInfiniteProgress();
                } else if (view == btnInfiniteStop) {
                    process.stopInfiniteProgress();
                }
            }
        };
        btnInfiniteStart.setOnClickListener(onClickListener);
        btnInfiniteStop.setOnClickListener(onClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        process.stop();
    }
}
