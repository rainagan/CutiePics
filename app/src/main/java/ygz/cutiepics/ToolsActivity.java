package ygz.cutiepics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Raina on 2018-01-25.
 */

public class ToolsActivity extends Activity {
    private Button sticker, frame, layout, doubleExpose;
    private String mCurrentPath;
    private String uri;
    private String type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        Bundle data = getIntent().getExtras();
        this.type = (String) data.get("type");
        if (type.equals("camera")) {
            Bundle captured = getIntent().getExtras();
            this.mCurrentPath = (String) captured.get("image");
        } else {
            Bundle imported = getIntent().getExtras();
            this.uri = (String) imported.get("image");
        }

        sticker = (Button) findViewById(R.id.stickers);
        sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToolsActivity.this, StickerActivity.class);
                intent.putExtra("type", type);
                if (type.equals("camera")) {
                    intent.putExtra("image", mCurrentPath);
                } else {
                    intent.putExtra("image", uri);
                }
                startActivity(intent);
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

    public void onBackPressed() {
        Intent intent = new Intent(ToolsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
