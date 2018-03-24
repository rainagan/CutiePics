package ygz.cutiepics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scrat.app.selectorlibrary.ImageSelector;

import java.util.List;

/**
 * Created by Raina on 2018-02-20.
 */

public class MultiPhotoSelectActivity extends Activity {
    private static final int REQUEST_CODE_SELECT_IMG = 1;
    private static final int MAX_SELECT_COUNT_LAYOUT = 4;
    private static final int MAX_SELECT_COUNT_OVERLAY = 2;

    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame2);

        Bundle temp = getIntent().getExtras();
        type = (String) temp.get("type");

        TextView mTextView = (TextView)findViewById(R.id.textMsg);
        String s;
        if (type.equals("layout")) {
            s = "Please select two to four photos >.<";
        } else {
            s = "Please select two photos >.<";
        }
        mTextView.setText(s);

        TextView myTextView = (TextView) findViewById(R.id.textMsg);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "unkempt.ttf");
        myTextView.setTypeface(typeface);
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

        Intent intent;
        if (type.equals("layout")) {
            intent = new Intent(MultiPhotoSelectActivity.this, LayoutActivity.class);
        } else {
            intent = new Intent(MultiPhotoSelectActivity.this, OverlayActivity.class);
        }
        String[] patharray = paths.toArray(new String[0]);
        intent.putExtra("photos", patharray);
        startActivity(intent);
    }

    public void selectImg(View v) {
        if (type.equals("layout")) {
            ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT_LAYOUT);
        } else {
            ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT_OVERLAY);
        }
    }
}

