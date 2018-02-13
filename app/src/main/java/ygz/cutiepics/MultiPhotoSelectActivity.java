//package ygz.cutiepics;
//
//import java.util.ArrayList;
//
//import android.app.Activity;
//import android.content.Context;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.util.SparseBooleanArray;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.SimpleAdapter;
//import android.widget.Toast;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.GridView;
//import android.widget.ImageView;
//
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
//
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
//
///**
// * @author Paresh Mayani (@pareshmayani)
// */
//public class MultiPhotoSelectActivity extends Activity {
////    private GridView gridView;
////    private ImageAdapter imageAdapter;
////    private static final int REQUEST_FOR_STORAGE_PERMISSION = 123;
////
////    @Override
////    public void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.layout_multi_photo_select);
////
////        populateImagesFromGallery();
////    }
////
////    public void btnChoosePhotosClick(View v){
////
////        ArrayList<String> selectedItems = imageAdapter.getCheckedItems();
////
////        if (selectedItems!= null && selectedItems.size() > 0) {
////            Toast.makeText(MultiPhotoSelectActivity.this, "Total photos selected: " + selectedItems.size(), Toast.LENGTH_SHORT).show();
////            Log.d(MultiPhotoSelectActivity.class.getSimpleName(), "Selected Items: " + selectedItems.toString());
////        }
////    }
////
////    private void populateImagesFromGallery() {
////        if (!mayRequestGalleryImages()) {
////            return;
////        }
////
////        ArrayList<String> imageUrls = loadPhotosFromNativeGallery();
////        initializeRecyclerView(imageUrls);
////    }
////
////    private boolean mayRequestGalleryImages() {
////
////        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
////            return true;
////        }
////
////        if (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
////            return true;
////        }
////
////        if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
////            //promptStoragePermission();
////            showPermissionRationaleSnackBar();
////        } else {
////            requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_FOR_STORAGE_PERMISSION);
////        }
////
////        return false;
////    }
////
////    /**
////     * Callback received when a permissions request has been completed.
////     */
////    @Override
////    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
////                                           @NonNull int[] grantResults) {
////
////        switch (requestCode) {
////
////            case REQUEST_FOR_STORAGE_PERMISSION: {
////
////                if (grantResults.length > 0) {
////                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                        populateImagesFromGallery();
////                    } else {
////                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
////                            showPermissionRationaleSnackBar();
////                        } else {
////                            Toast.makeText(this, "Go to settings and enable permission", Toast.LENGTH_LONG).show();
////                        }
////                    }
////                }
////
////                break;
////            }
////        }
////    }
////
////    private ArrayList<String> loadPhotosFromNativeGallery() {
////        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
////        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
////        Cursor imagecursor = managedQuery(
////                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
////                null, orderBy + " DESC");
////
////        ArrayList<String> imageUrls = new ArrayList<String>();
////
////        for (int i = 0; i < imagecursor.getCount(); i++) {
////            imagecursor.moveToPosition(i);
////            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
////            imageUrls.add(imagecursor.getString(dataColumnIndex));
////
////            System.out.println("=====> Array path => "+imageUrls.get(i));
////        }
////
////        return imageUrls;
////    }
////
////    private void initializeRecyclerView(ArrayList<String> imageUrls) {
////        imageAdapter = new ImageAdapter(this, imageUrls);
////
////        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),4);
////        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
////        recyclerView.setLayoutManager(layoutManager);
////        recyclerView.setItemAnimator(new DefaultItemAnimator());
////        recyclerView.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.item_offset));
////        recyclerView.setAdapter(imageAdapter);
////    }
////
////    private void showPermissionRationaleSnackBar() {
////        Snackbar.make(findViewById(R.id.button1), getString(R.string.permission_rationale),
////                Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                // Request the permission
////                ActivityCompat.requestPermissions(MultiPhotoSelectActivity.this,
////                        new String[]{READ_EXTERNAL_STORAGE},
////                        REQUEST_FOR_STORAGE_PERMISSION);
////            }
////        }).show();
////
////    }
//
//    private GridView gridView1;              //网格显示缩略图
//    private Button buttonPublish;            //发布按钮
//    private final int IMAGE_OPEN = 1;        //打开图片标记
//    private String pathImage;                //选择图片路径
//    private Bitmap bmp;                      //导入临时图片
//    private ArrayList<HashMap<String, Object>> imageItem;
//    private SimpleAdapter simpleAdapter;     //适配器
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        /*
//         * 防止键盘挡住输入框
//         * 不希望遮挡设置activity属性 android:windowSoftInputMode="adjustPan"
//         * 希望动态调整高度 android:windowSoftInputMode="adjustResize"
//         */
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
//        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.gridview_addpic);
//        imageItem = new ArrayList<HashMap<String, Object>>();
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("itemImage", bmp);
//        imageItem.add(map);
//        simpleAdapter = new SimpleAdapter(this,
//                imageItem, R.layout.griditem_addpic,
//                new String[] { "itemImage"}, new int[] { R.id.imageView1});
//        /*
//         * HashMap载入bmp图片在GridView中不显示,但是如果载入资源ID能显示 如
//         * map.put("itemImage", R.drawable.img);
//         * 解决方法:
//         *              1.自定义继承BaseAdapter实现
//         *              2.ViewBinder()接口实现
//         *  参考 http://blog.csdn.net/admin_/article/details/7257901
//         */
//        simpleAdapter.setViewBinder(new ViewBinder() {
//            @Override
//            public boolean setViewValue(View view, Object data,
//                                        String textRepresentation) {
//                // TODO Auto-generated method stub
//                if(view instanceof ImageView && data instanceof Bitmap){
//                    ImageView i = (ImageView)view;
//                    i.setImageBitmap((Bitmap) data);
//                    return true;
//                }
//                return false;
//            }
//        });
//        gridView1.setAdapter(simpleAdapter);
//
//        /*
//         * 监听GridView点击事件
//         * 报错:该函数必须抽象方法 故需要手动导入import android.view.View;
//         */
//        gridView1.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
//            {
//                if( imageItem.size() == 10) { //第一张为默认图片
//                    Toast.makeText(MainActivity.this, "图片数9张已满", Toast.LENGTH_SHORT).show();
//                }
//                else if(position == 0) { //点击图片位置为+ 0对应0张图片
//                    Toast.makeText(MainActivity.this, "添加图片", Toast.LENGTH_SHORT).show();
//                    //选择图片
//                    Intent intent = new Intent(Intent.ACTION_PICK,
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, IMAGE_OPEN);
//                    //通过onResume()刷新数据
//                }
//                else {
//                    dialog(position);
//                    //Toast.makeText(MainActivity.this, "点击第"+(position + 1)+" 号图片",
//                    //      Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    //获取图片路径 响应startActivityForResult
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //打开图片
//        if(resultCode==RESULT_OK && requestCode==IMAGE_OPEN) {
//            Uri uri = data.getData();
//            if (!TextUtils.isEmpty(uri.getAuthority())) {
//                //查询选择图片
//                Cursor cursor = getContentResolver().query(
//                        uri,
//                        new String[] { MediaStore.Images.Media.DATA },
//                        null,
//                        null,
//                        null);
//                //返回 没找到选择图片
//                if (null == cursor) {
//                    return;
//                }
//                //光标移动至开头 获取图片路径
//                cursor.moveToFirst();
//                pathImage = cursor.getString(cursor
//                        .getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//        }  //end if 打开图片
//    }
//
//    //刷新图片
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(!TextUtils.isEmpty(pathImage)){
//            Bitmap addbmp=BitmapFactory.decodeFile(pathImage);
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("itemImage", addbmp);
//            imageItem.add(map);
//            simpleAdapter = new SimpleAdapter(this,
//                    imageItem, R.layout.griditem_addpic,
//                    new String[] { "itemImage"}, new int[] { R.id.imageView1});
//            simpleAdapter.setViewBinder(new ViewBinder() {
//                @Override
//                public boolean setViewValue(View view, Object data,
//                                            String textRepresentation) {
//                    // TODO Auto-generated method stub
//                    if(view instanceof ImageView && data instanceof Bitmap){
//                        ImageView i = (ImageView)view;
//                        i.setImageBitmap((Bitmap) data);
//                        return true;
//                    }
//                    return false;
//                }
//            });
//            gridView1.setAdapter(simpleAdapter);
//            simpleAdapter.notifyDataSetChanged();
//            //刷新后释放防止手机休眠后自动添加
//            pathImage = null;
//        }
//    }
//
//    /*
//     * Dialog对话框提示用户删除操作
//     * position为删除图片位置
//     */
//    protected void dialog(final int position) {
//        AlertDialog.Builder builder = new Builder(MainActivity.this);
//        builder.setMessage("确认移除已添加图片吗？");
//        builder.setTitle("提示");
//        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                imageItem.remove(position);
//                simpleAdapter.notifyDataSetChanged();
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.create().show();
//    }
//}