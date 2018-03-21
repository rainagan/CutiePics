package ygz.cutiepics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Raina on 2018-02-27.
 */

public class LayoutActivity extends Activity {
    private ImageView img;
    private int picNum = 0;
    private List<String> patharr = new ArrayList<>();;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        img = (ImageView) findViewById(R.id.ivImage);

        Bundle captured = getIntent().getExtras();
        String[] temparr = (String[]) captured.get("photos");
        patharr = Arrays.asList(temparr);
        picNum = patharr.size();

        // For the recycler view part, layout will reuse the code of frame
        RecyclerView rv = (RecyclerView) findViewById(R.id.frame_view);
        GridLayoutManager mGrid = new GridLayoutManager(this, 4);
        rv.setLayoutManager(mGrid);
        rv.setHasFixedSize(true);
        FrameAdapter mAdapter;
        if (patharr.size() == 2) {
            mAdapter = new FrameAdapter(LayoutActivity.this, getFrameTestData2());
        } else if (patharr.size() == 3) {
            mAdapter = new FrameAdapter(LayoutActivity.this, getFrameTestData3());
        } else {
            mAdapter = new FrameAdapter(LayoutActivity.this, getFrameTestData4());
        }

        rv.setAdapter(mAdapter);

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    public void onItemClick(View view, int position) {
                        // First consider two picture option for now
                        //if (position == 0) {
                        //setLayout2();
                        Bitmap combined = useLayout(position);

                        if (combined == null) {
                            Log.d("Error", "position now is "+position);
                            return;
                        }

                        img.setImageDrawable(new BitmapDrawable(getResources(), combined));
                        //img.setImageDrawable(new BitmapDrawable(getResources(), combined));


                        BitmapDrawable frame_origin = (BitmapDrawable) img.getDrawable();
                        Bitmap saved_bitmap = frame_origin.getBitmap();
                        photoModel.setmPhoto(saved_bitmap);
                    }

                    public void onLongItemClick(View view, int position) {
//                        // do whatever
                    }
                })
        );

    }

    private Bitmap useLayout(int position) {
        if (picNum == 2 && position == 0) {
            Bitmap pic1 = getBitmap(patharr.get(0));
            Bitmap pic2 = getBitmap(patharr.get(1));
            Layout_Two layout = new Layout_Two();
            return layout.combineImages(pic1, pic2);
        }
        return null;
    }

    /*
    public void setLayout2() {
        Bitmap pic1 = getBitmap(patharr.get(0));
        Bitmap pic2 = getBitmap(patharr.get(1));

        Bitmap combined = combineImages(pic1, pic2);
        img.setImageDrawable(new BitmapDrawable(getResources(), combined));
        img.setImageDrawable(new BitmapDrawable(getResources(), combined));
    }
    */

    public Bitmap getBitmap(String path) {
        try {
            Bitmap bitmap=null;
            File f= new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 3;   // Bitmap we get is compressed

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
/*
    public Bitmap combineImages(Bitmap c, Bitmap s) {
        Bitmap cs;

        int width, height = 0;

        if(c.getWidth() > s.getWidth()) {
            width = c.getWidth() + s.getWidth();
            height = c.getHeight();
        } else {
            width = s.getWidth() + s.getWidth();
            height = c.getHeight();
        }


        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, c.getWidth(), 0f, null);

        return cs;
    }
*/

    private ArrayList<FrameObject> getFrameTestData2() {
        ArrayList<FrameObject> featuredFrame = new ArrayList<FrameObject>();
        featuredFrame.add(new FrameObject("layout_2_1"));
        featuredFrame.add(new FrameObject("layout_2"));

        return featuredFrame;
    }

    private ArrayList<FrameObject> getFrameTestData3() {
        ArrayList<FrameObject> featuredFrame = new ArrayList<FrameObject>();
        featuredFrame.add(new FrameObject("layout_3"));

        return featuredFrame;
    }

    private ArrayList<FrameObject> getFrameTestData4() {
        ArrayList<FrameObject> featuredFrame = new ArrayList<FrameObject>();
        featuredFrame.add(new FrameObject("layout_4"));

        return featuredFrame;
    }

    public void saveImg(View view) {
        Intent intent = new Intent(LayoutActivity.this, SavePhoto.class);
        startActivity(intent);
    }

}
