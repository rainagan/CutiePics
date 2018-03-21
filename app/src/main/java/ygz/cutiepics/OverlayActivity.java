package ygz.cutiepics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Raina on 2018-03-18.
 */

public class OverlayActivity extends Activity {
    private SeekBar seekBar;
    private RadioGroup radGroup;
    private RadioButton rad1;
    private RadioButton rad2;

    private ImageView img1;
    private ImageView img2;

    private int prog;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_overlay);

        img1 = (ImageView) findViewById(R.id.overlayImg1);
        img2 = (ImageView) findViewById(R.id.overlayImg2);

        Bundle captured = getIntent().getExtras();
        List<String> patharr = new ArrayList<>();
        String[] temparr = (String[]) captured.get("photos");
        patharr = Arrays.asList(temparr);

        String temp1 = patharr.get(0);
        Uri tempUri1 = Uri.parse(temp1);
        img1.setImageURI(tempUri1);

        String temp2 = patharr.get(1);
        Uri tempUri2 = Uri.parse(temp2);
        img2.setImageURI(tempUri2);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        radGroup = findViewById(R.id.radGroup);
        radGroup.setOnCheckedChangeListener(radGroupListener);

        rad1 = findViewById(R.id.overlayBut1);
        rad2 = findViewById(R.id.overlayBut2);
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
            prog = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // when the user first touches the seekbar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // when the user last touches the seekbar
            Toast.makeText(OverlayActivity.this, "Exposure " + Integer.toString(prog), Toast.LENGTH_SHORT).show();
        }
    };

    RadioGroup.OnCheckedChangeListener radGroupListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton radBut = (RadioButton) findViewById(checkedId);
            Toast.makeText(OverlayActivity.this, "You selected " + radBut.getText(), Toast.LENGTH_SHORT);
        }
    };
}
