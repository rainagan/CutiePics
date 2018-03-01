package ygz.cutiepics;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yuxiao Yu on 2018-02-01.
 */

public class FrameActivity extends Activity {
    private ImageView img;
    private Drawable origin;

    private boolean added = false;
    private int add_pos = -1;
    private String mCurrentPath;
    //private PopupWindow pw;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        img = (ImageView) findViewById(R.id.ivImage);

        Bundle captured = getIntent().getExtras();
        this.mCurrentPath = (String) captured.get("image");
        Uri uriFromPath = Uri.fromFile(new File(mCurrentPath));
        img.setImageURI(uriFromPath);

        origin = img.getDrawable();

        final RecyclerView rv = (RecyclerView) findViewById(R.id.frame_view);
        GridLayoutManager mGrid = new GridLayoutManager(this, 4);
        rv.setLayoutManager(mGrid);
        rv.setHasFixedSize(true);
        FrameAdapter mAdapter = new FrameAdapter(FrameActivity.this, getFrameTestData());
        rv.setAdapter(mAdapter);

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    public void onItemClick(View view, int position) {
                        FrameViewHolder fvh = (FrameViewHolder) rv.findViewHolderForAdapterPosition(position);
                        ImageView temp = fvh.getFrame();
                        BitmapDrawable frame_origin = (BitmapDrawable) temp.getDrawable();
                        Bitmap frame = frame_origin.getBitmap();

                        if (added == true && position == add_pos) {

                                added = false;
                                img.setImageDrawable(origin);
                                return;

                        }

                        if (position != RecyclerView.NO_POSITION) {
                            added = true;
                            add_pos = position;
                            addFrame(frame);
                        }

                        //frame.recycle();
                    }

                    public void onLongItemClick(View view, int position) {
//                        // do whatever
                    }
                })
        );

    }

    private void addFrame(Bitmap frame) {
        Drawable image = origin;
        Drawable[] array = new Drawable[2];

        Bitmap image_bm = ((BitmapDrawable)image).getBitmap();

        Bitmap frame_bm = resize(frame, image_bm.getWidth(), image_bm.getHeight());



        array[0] = image;
        array[1] = new BitmapDrawable(getResources(), frame_bm);
        LayerDrawable layer = new LayerDrawable(array);
        img.setImageDrawable(layer);
        //image_bm.recycle();
        //frame_bm.recycle();
    }

    // This is a helper function copied from on line
    public Bitmap resize(Bitmap bm, int w, int h)
    {

        Bitmap BitmapOrg = bm;

        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // if you want to rotate the Bitmap
        // matrix.postRotate(45);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;

    }

    private ArrayList<FrameObject> getFrameTestData() {
        ArrayList<FrameObject> featuredFrame = new ArrayList<FrameObject>();
        featuredFrame.add(new FrameObject("frame_pure2"));
        featuredFrame.add(new FrameObject("leafframe"));
        //featuredFrame.add(new FrameObject("frame_pure1"));

        return featuredFrame;
    }
}

