package ygz.cutiepics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuxiao Yu on 2018-02-01.
 */

public class FrameActivity extends Activity {
    private ImageView img;
    private String mCurrentPath;
    private String uri;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        img = (ImageView) findViewById(R.id.ivImage);

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

        RecyclerView rv = (RecyclerView) findViewById(R.id.pop_sticker);

        // use a grid layout manager
        GridLayoutManager mGrid = new GridLayoutManager(this, 4);
        rv.setLayoutManager(mGrid);
        // rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);

        // specify an adapter
        FrameAdapter mAdapter = new FrameAdapter(FrameActivity.this, getFrameTestData());
        rv.setAdapter(mAdapter);

        //Log.d("Debug", "Finish Frame Activity");
    }

    private ArrayList<FrameObject> getFrameTestData() {
        ArrayList<FrameObject> featuredFrame = new ArrayList<FrameObject>();
        featuredFrame.add(new FrameObject("frame_pure1"));
        featuredFrame.add(new FrameObject("frame_pure2"));

        return featuredFrame;
    }

}

