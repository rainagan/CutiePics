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
        GridLayoutManager mGrid = new GridLayoutManager(this, 8);
        rv.setLayoutManager(mGrid);
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);
        ProductAdapter mAdapter = new ProductAdapter(StickerActivity.this, getProductTestData());
        rv.setAdapter(mAdapter);

//        img.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // initialize a popup window
//                stickerWindow = new SelectStickerPopupWindow(StickerActivity.this, itemsOnClick);
//                // show popup window
//                stickerWindow.showAtLocation(StickerActivity.this.findViewById(R.id.ivImage), Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
//            }
//        });
    }

    private ArrayList<ProductObject> getProductTestData() {
        ArrayList<ProductObject> featuredProducts = new ArrayList<ProductObject>();
        featuredProducts.add(new ProductObject("yellowbook"));
        featuredProducts.add(new ProductObject("redbook"));
        featuredProducts.add(new ProductObject("bluebook"));
        featuredProducts.add(new ProductObject("cardspade"));
        featuredProducts.add(new ProductObject("cardclub"));
        featuredProducts.add(new ProductObject("cardheart"));
        featuredProducts.add(new ProductObject("carddiamond"));
        featuredProducts.add(new ProductObject("beach"));
        return featuredProducts;
    }

//    private View.OnClickListener itemsOnClick = new View.OnClickListener(){
//        public void onClick(View v) {
//            stickerWindow.dismiss();
//            switch (v.getId()) {
////                case R.id.yellowbook:
////                    Log.d("button press", "yellowbook is pressed");
////                    break;
////                case R.id.btn2:
////                    break;
//                default:
//                    break;
//            }
//        }
//    };
}

