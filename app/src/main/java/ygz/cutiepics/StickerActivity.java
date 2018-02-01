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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
    private SelectStickerPopupWindow stickerWindow;
    private Button yellowBook;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers);

        img = (ImageView) findViewById(R.id.ivImage);

        yellowBook = (Button) findViewById(R.id.yellowbook);

        Bundle data = getIntent().getExtras();
        this.type = (String) data.get("type");
        if (type.equals("camera")) {
            Bundle captured = getIntent().getExtras();
            this.mCurrentPath = (String) captured.get("image");
            File imgFile = new File(mCurrentPath);
            if (imgFile.exists()) {
                Bitmap mBitMap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                img.setImageBitmap(mBitMap);
            }
        } else {
            Bundle imported = getIntent().getExtras();
            this.uri = (String) imported.get("image");
            Uri uriFromPath = Uri.fromFile(new File(uri));
            img.setImageURI(uriFromPath);
        }

        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // initialize a popup window
                stickerWindow = new SelectStickerPopupWindow(StickerActivity.this, itemsOnClick);
                // show popup window
                stickerWindow.showAtLocation(StickerActivity.this.findViewById(R.id.ivImage), Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener(){
        public void onClick(View v) {
            stickerWindow.dismiss();
            switch (v.getId()) {
                case R.id.yellowbook:
                    Log.d("button press", "yellowbook is pressed");
                    break;
//                case R.id.btn2:
//                    break;
                default:
                    break;
            }
        }
    };
}

