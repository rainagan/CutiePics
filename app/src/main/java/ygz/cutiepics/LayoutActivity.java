package ygz.cutiepics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ygz.cutiepics.models.FrameObject;
import ygz.cutiepics.models.PhotoModel;

/**
 * Created by Raina on 2018-02-27.
 */

public class LayoutActivity extends Activity {
    private ImageView img;
    private int picNum = 0;
    private List<String> patharr = new ArrayList<>();

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

                        Bitmap combined = useLayout(position);

                        if (combined == null) {
                            Log.d("Error", "position now is "+position);
                            return;
                        }

                        img.setImageDrawable(new BitmapDrawable(getResources(), combined));

                        BitmapDrawable frame_origin = (BitmapDrawable) img.getDrawable();
                        Bitmap saved_bitmap = frame_origin.getBitmap();
                        PhotoModel.setmPhoto(saved_bitmap);
                    }

                    public void onLongItemClick(View view, int position) {
//                        // do whatever
                    }
                })
        );

    }

    private Bitmap useLayout(int position) {
        LayoutTemplate layout = new LayoutTemplate();
        layout.position = position;
        return layout.match_temp(this.patharr, getBaseContext());  // should we use base context here?
    }

    private ArrayList<FrameObject> getFrameTestData2() {
        ArrayList<FrameObject> featuredFrame = new ArrayList<FrameObject>();
        featuredFrame.add(new FrameObject("layout2_1"));
        featuredFrame.add(new FrameObject("layout2_2"));

        return featuredFrame;
    }

    private ArrayList<FrameObject> getFrameTestData3() {
        ArrayList<FrameObject> featuredFrame = new ArrayList<FrameObject>();
        featuredFrame.add(new FrameObject("layout3_1"));
        featuredFrame.add(new FrameObject("layout3_2"));
        featuredFrame.add(new FrameObject("layout3_3"));
        featuredFrame.add(new FrameObject("layout3_4"));

        return featuredFrame;
    }

    private ArrayList<FrameObject> getFrameTestData4() {
        ArrayList<FrameObject> featuredFrame = new ArrayList<FrameObject>();
        featuredFrame.add(new FrameObject("layout4_1"));

        return featuredFrame;
    }

    public void saveImg(View view) {
        Intent intent = new Intent(LayoutActivity.this, SavePhotoActivity.class);
        startActivity(intent);
    }

}
