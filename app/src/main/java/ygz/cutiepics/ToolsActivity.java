package ygz.cutiepics;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Raina on 2018-01-25.
 */

public class ToolsActivity extends Activity {
    private Button camera, gallery;
    private int REQUEST_CAMERA = 0;
    private final int SELECT_FILE = 1;
    public final int PHOTO_GALLERY_REQUEST = 20;
    private String mCurrentPhotoPath;
    private String type;
    private final Handler handler = new Handler();
    private File photoFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        Bundle tp = getIntent().getExtras();
        type = (String) tp.get("type");

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SELECT_FILE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);

        camera = (Button) findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntent();
            }
        });

        gallery = (Button) findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryIntent();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraIntent();
                } else {
                    Toast.makeText(ToolsActivity.this, "Taking photo permision is denined", Toast.LENGTH_SHORT);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                onCaptureImageResult();
                Intent intent;
                if (type.equals("sticker")) {
                    intent = new Intent(ToolsActivity.this, StickerActivity.class);
                } else {
                    intent = new Intent(ToolsActivity.this, FrameActivity.class);
                }
                intent.putExtra("image", mCurrentPhotoPath);
                startActivity(intent);
            }
        } else {
            if (resultCode == Activity.RESULT_OK && data != null) {
                final Uri imageUri = data.getData();
                mCurrentPhotoPath = getRealPathFromURI(imageUri);
                returnURI(mCurrentPhotoPath);
            }
        }
    }

    private void onCaptureImageResult() {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void cameraIntent() {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // ensure that htere's a camera activity to hadle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        // create the File where the photo should go
                        photoFile = createImageFile();
                    } catch (IOException e) {
                        // error occurred while creating the File
                        Log.e("IOException", "unable to create image file");
                        e.printStackTrace();
                    }
                    // continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(ToolsActivity.this, "com.example.android.fileprovider", photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }
                }
            }, 0);
        }
    }

    private File createImageFile() throws IOException {
        // create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imgFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imgFileName, ".jpg", storageDir);

        // save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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
        Intent intent;
        if (type.equals("sticker")) {
            intent = new Intent(ToolsActivity.this, StickerActivity.class);
        } else {
            intent = new Intent(ToolsActivity.this, FrameActivity.class);
        }
        intent.putExtra("image", mCurrentPhotoPath);
        startActivity(intent);
    }

    public void onBackPressed() {
        Intent intent = new Intent(ToolsActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
