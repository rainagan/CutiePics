package ygz.cutiepics;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Raina on 2018-02-27.
 */

public class LayoutActivity extends Activity {
    private PopupWindow pw;
    private int picNum = 0;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_frame);

        Bundle captured = getIntent().getExtras();
        List<String> patharr = new ArrayList<>();
        String[] temparr = (String[]) captured.get("photos");
        patharr = Arrays.asList(temparr);

        TableLayout table = (TableLayout) findViewById(R.id.table);
        for (int i = 0; i < patharr.size(); i++) {
            final String temp = patharr.get(i);
            final Uri tempUri = Uri.parse(temp);

            final ImageView img = new ImageView(this);
            img.setImageURI(tempUri);

            final TableRow tr = new TableRow(this);
            tr.addView(img);
            table.addView(tr);
        }

        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "On click check");
                showPopupWindow();
            }
        });
    }

    private void showPopupWindow() {
        View view = LayoutInflater.from(LayoutActivity.this).inflate(R.layout.sticker_popup, null);
        pw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new ColorDrawable(0xffffff));
        pw.setOutsideTouchable(true);
        pw.setAnimationStyle(R.style.Animation);
        pw.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.pop_sticker);
        GridLayoutManager mGrid = new GridLayoutManager(this, 8);
        rv.setLayoutManager(mGrid);
//        ScoreTeamAdapter scoreTeamAdapter = new ScoreTeamAdapter(yearList);
//        rv.setAdapter(scoreTeamAdapter);
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(32);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rv.setNestedScrollingEnabled(false);
        FrameAdapter mAdapter = new FrameAdapter(LayoutActivity.this, getFrameTestData());
        rv.setAdapter(mAdapter);
    }

    private ArrayList<FrameObject> getFrameTestData() {
        ArrayList<FrameObject> featuredFrame = new ArrayList<FrameObject>();
        featuredFrame.add(new FrameObject("frame_pure1"));
        featuredFrame.add(new FrameObject("frame_pure2"));

        return featuredFrame;
    }
}
