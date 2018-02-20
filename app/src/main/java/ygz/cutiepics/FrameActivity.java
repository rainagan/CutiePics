package ygz.cutiepics;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yuxiao Yu on 2018-02-01.
 */

public class FrameActivity extends Activity {
    //    private ImageView img;
//    private String mCurrentPath;
    private GridView gridView1;              //网格显示缩略图
    private Button addFramePic;            //发布按钮
    private Button addFrame;
    private final int IMAGE_OPEN = 1;        //打开图片标记
    private String pathImage;                //选择图片路径
    private Bitmap bmp;                      //导入临时图片
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;     //适配器
    private PopupWindow pw;
    private final int SELECT_FILE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SELECT_FILE);

        addFramePic = (Button) findViewById(R.id.addFramePic);
        addFramePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FrameActivity.this, "Add photo", Toast.LENGTH_SHORT).show();
                //select photo from gallery
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_OPEN);
                //refresh data when calling onResume()
            }
        });

//        img = (ImageView) findViewById(R.id.ivImage);

//        Bundle captured = getIntent().getExtras();
//        this.mCurrentPath = (String) captured.get("image");
//        Uri uriFromPath = Uri.fromFile(new File(mCurrentPath));
//        img.setImageURI(uriFromPath);

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.
//                SOFT_INPUT_ADJUST_PAN);
//        //锁定屏幕
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        setContentView(R.layout.activity_main);
//        //获取控件对象
//        gridView1 = (GridView) findViewById(R.id.gridView1);
//
//        /*
//         * 载入默认图片添加图片加号
//         * 通过适配器实现
//         * SimpleAdapter参数imageItem为数据源 R.layout.griditem_addpic为布局
//         */
//        //获取资源图片加号
//        imageItem = new ArrayList<HashMap<String, Object>>();
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("itemImage", bmp);
//        imageItem.add(map);
//        simpleAdapter = new SimpleAdapter(this,
//                imageItem, R.layout.grid_pic_view,
//                new String[] { "itemImage"}, new int[] { R.id.imageView1});
//
//        /*
//         * HashMap载入bmp图片在GridView中不显示,但是如果载入资源ID能显示 如
//         * map.put("itemImage", R.drawable.img);
//         * 解决方法:
//         *              1.自定义继承BaseAdapter实现
//         *              2.ViewBinder()接口实现
//         *  参考 http://blog.csdn.net/admin_/article/details/7257901
//         */
////        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
////            @Override
////            public boolean setViewValue(View view, Object data,
////                                        String textRepresentation) {
////                // TODO Auto-generated method stub
////                if(view instanceof ImageView && data instanceof Bitmap){
////                    ImageView i = (ImageView)view;
////                    i.setImageBitmap((Bitmap) data);
////                    return true;
////                }
////                return false;
////            }
////        });
////        gridView1.setAdapter(simpleAdapter);
//
////        RecyclerView rv = (RecyclerView) findViewById(R.id.pop_sticker);
////
////        // use a grid layout manager
////        GridLayoutManager mGrid = new GridLayoutManager(this, 4);
////        rv.setLayoutManager(mGrid);
////        // rv.setHasFixedSize(true);
////        rv.setNestedScrollingEnabled(false);
////
////        // specify an adapter
////        FrameAdapter mAdapter = new FrameAdapter(FrameActivity.this, getFrameTestData());
////        rv.setAdapter(mAdapter);
//
//        //Log.d("Debug", "Finish Frame Activity");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // open image
        if (resultCode == RESULT_OK && requestCode == IMAGE_OPEN) {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                // check selected image
                Cursor cursor = getContentResolver().query(
                        uri,
                        new String[]{MediaStore.Images.Media.DATA},
                        null,
                        null,
                        null);
                // return if nothing is selected
                if (null == cursor) {
                    return;
                }
                // cursor move to first selected photo and get path
                cursor.moveToFirst();
                pathImage = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
            }

            showPopupWindow();
        }
    }

    // refresh photo
    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(pathImage)) {
            Bitmap addbmp = BitmapFactory.decodeFile(pathImage);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", addbmp);
            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(this,
                    imageItem, R.layout.grid_pic_view,
                    new String[]{"itemImage"}, new int[]{R.id.imageView1});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    // TODO Auto-generated method stub
                    if (view instanceof ImageView && data instanceof Bitmap) {
                        ImageView i = (ImageView) view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView1.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
            //刷新后释放防止手机休眠后自动添加
            pathImage = null;
        }
    }

    private void showPopupWindow() {
        View view = LayoutInflater.from(FrameActivity.this).inflate(R.layout.sticker_popup, null);
        pw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, 450);
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new ColorDrawable(0x969b9b));
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
        FrameAdapter mAdapter = new FrameAdapter(FrameActivity.this, getFrameTestData());
        rv.setAdapter(mAdapter);
    }

    private ArrayList<FrameObject> getFrameTestData() {
        ArrayList<FrameObject> featuredFrame = new ArrayList<FrameObject>();
        featuredFrame.add(new FrameObject("frame_pure1"));
        featuredFrame.add(new FrameObject("frame_pure2"));

        return featuredFrame;
    }
}

