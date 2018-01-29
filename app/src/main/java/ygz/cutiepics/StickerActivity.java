package ygz.cutiepics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Raina on 2018-01-29.
 */

public class StickerActivity extends Activity {
    private ImageView img;
    private String mCurrentPath;
    private String uri;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers);

        img = (ImageView) findViewById(R.id.ivImage);

        Bundle data = getIntent().getExtras();
        this.type = (String) data.get("type");
        if (type.equals("camera")) {
            Bundle captured = getIntent().getExtras();
            this.mCurrentPath = (String) captured.get("image");
//            Log.d("path", mCurrentPath);
            File imgFile = new File(mCurrentPath);
            if (imgFile.exists()) {
                Bitmap mBitMap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                img.setImageBitmap(mBitMap);
            }
        } else {
            Bundle imported = getIntent().getExtras();
            this.uri = (String) imported.get("image");
//            Log.d("uri", uri);
            Uri uriFromPath = Uri.fromFile(new File(uri));
            img.setImageURI(uriFromPath);
        }
    }
}

