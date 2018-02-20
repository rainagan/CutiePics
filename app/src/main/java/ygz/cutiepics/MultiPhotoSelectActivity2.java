package ygz.cutiepics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scrat.app.selectorlibrary.ImageSelector;

import java.util.List;

/**
 * Created by Raina on 2018-02-20.
 */

public class MultiPhotoSelectActivity2 extends Activity {
    private static final int REQUEST_CODE_SELECT_IMG = 1;
    private static final int MAX_SELECT_COUNT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SELECT_IMG) {
            showContent(data);
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showContent(Intent data) {
        List<String> paths = ImageSelector.getImagePaths(data);

        Intent intent = new Intent(MultiPhotoSelectActivity2.this, FrameActivity.class);
        String[] patharray = paths.toArray(new String[0]);
        intent.putExtra("photos", patharray);
        startActivity(intent);
    }

    public void selectImg(View v) {
        ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT);
    }
}

