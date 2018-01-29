package ygz.cutiepics;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.widget.Gallery;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

/**
 * Created by Raina on 2018-01-29.
 */

public class GalleryActivity extends Activity {
    private final int SELECT_FILE = 1;
    private ImageView ivImage;
    private String path;
    public final int PHOTO_GALLERY_REQUEST = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        galleryIntent();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
//                } else {
                    //code for deny
                }
                break;
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();

        Uri data = Uri.parse(pictureDirectoryPath);        
        intent.setType("image/*");
        intent.setDataAndType(data, "image/*");
        startActivityForResult(intent, PHOTO_GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            // onSelectFromGalleryResult(data);
            Uri imageUri = data.getData();
            this.path = getRealPathFromURI(imageUri);
            returnURI(this.path);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        ivImage.setImageBitmap(bm);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public void returnURI(String uri) {
        Intent intent = new Intent(GalleryActivity.this, ToolsActivity.class);
        intent.putExtra("image", uri);
        intent.putExtra("type", "gallery");
        startActivity(intent);
    }

    public void onBackPressed() {
        Intent intent = new Intent(GalleryActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
