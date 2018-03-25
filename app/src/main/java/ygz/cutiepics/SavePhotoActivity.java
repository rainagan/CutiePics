package ygz.cutiepics;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import ygz.cutiepics.models.PhotoModel;
import ygz.cutiepics.models.UploadInfo;

/**
 * Created by yuyuxiao on 2018-03-20.
 *
 * This class is used for savedPhoto activity
 */

public class SavePhotoActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageReference fileRef;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_photo);

        Bitmap bmp = PhotoModel.getmPhoto();
        saveImageToGallery(bmp);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference().child("images");
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("images");

        progressDialog = new ProgressDialog(this);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mUser = mAuth.getCurrentUser();
    }


    public String saveImageToGallery(Bitmap saved) {
        String result = MediaStore.Images.Media.insertImage(getContentResolver(), saved, "" , "");
        return result;
    }

    public void editNext(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        PhotoModel.getmPhoto().recycle();
    }

    public void sharePhoto(View view) {
        // TODO: share photo to groups
        Uri photo = getImageUri(SavePhotoActivity.this, PhotoModel.getmPhoto());
        uploadImageAsyncTask uiat = new uploadImageAsyncTask();
        uiat.execute(photo);
    }

    private class uploadImageAsyncTask extends AsyncTask<Uri, String, String> {
        @Override
        protected String doInBackground(Uri... uris) {
            Uri filePath = uris[0];
            if(filePath != null) {
                Random generator = new Random();
                int n = Integer.MAX_VALUE;
                n = generator.nextInt(n);
                String fname = "Image" + n;
                fileRef = storageReference.child(fname);

                fileRef.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                String name = taskSnapshot.getMetadata().getName();
                                String url = taskSnapshot.getDownloadUrl().toString();

                                writeNewImageInfoToDB(name, url);

                                Toast.makeText(SavePhotoActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SavePhotoActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded "+(int)progress+"%");

                            }
                        });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Uploading...");
            progressDialog.setMessage(null);
            progressDialog.show();
            progressDialog.setProgress(0);
        }
    }

    private void writeNewImageInfoToDB(String name, String url) {
        UploadInfo info = new UploadInfo(name, url);

        String key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(info);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
