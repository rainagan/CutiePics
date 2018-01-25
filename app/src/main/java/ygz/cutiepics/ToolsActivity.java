package ygz.cutiepics;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by Raina on 2018-01-25.
 */

public class ToolsActivity extends Activity {
    private Button sticker, frame, layout, doubleExpose;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        sticker = (Button) findViewById(R.id.stickers);
        sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do staff here
            }
        });

        frame = (Button) findViewById(R.id.frames);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do staff here
            }
        });

        layout = (Button) findViewById(R.id.layouts);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do staff here
            }
        });

        doubleExpose = (Button) findViewById(R.id.doubleExposure);
        doubleExpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do staff here
            }
        });
    }
}
