package com.example.preinterviewtask.ui.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.preinterviewtask.MyGlideEngine;
import com.example.preinterviewtask.Object.Item;
import com.example.preinterviewtask.Types.Keys;
import com.example.preinterviewtask.R;
import com.example.preinterviewtask.Units.Utility;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.IOException;
import java.io.InputStream;

import static com.example.preinterviewtask.ui.Activites.MainActivity.TYPE;
import static com.example.preinterviewtask.ui.Activites.MainActivity.ITEM;

public class DetailsActivity extends AppCompatActivity {

    final static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 300;
    public static int REQUEST_CODE_CHOOSE = 200;
    Uri SelectedImage;
    ImageView imageAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //// define
        imageAvatar = findViewById(R.id.imageAvatar);
        Button save = findViewById(R.id.save);
        EditText title = findViewById(R.id.title);
        ///extra data
        Intent intent = getIntent();

        int type = intent.getIntExtra(TYPE, 0);

        if (type == Keys.viewType) {
            Item item = (Item) intent.getSerializableExtra(ITEM);
            if (item != null) {
                putImage(item.url,imageAvatar);
                title.setText(item.title);
                title.setEnabled(false);
                imageAvatar.setEnabled(false);
            }
        } else if (type == Keys.updateType) {
            Item item = (Item) intent.getSerializableExtra(ITEM);
            if (item != null) {
                putImage(item.url,imageAvatar);
                title.setText(item.title);
                save.setVisibility(View.VISIBLE);
            }
        } else if (type != 0) {
            save.setVisibility(View.VISIBLE);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissionREAD_EXTERNAL_STORAGE(DetailsActivity.this))
                    Matisse.from(DetailsActivity.this)
                            .choose(MimeType.of(MimeType.JPEG))
                            .countable(true)
                            .maxSelectable(1)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                            .thumbnailScale(0.85f)
                            .imageEngine(new MyGlideEngine())
                            .forResult(REQUEST_CODE_CHOOSE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            SelectedImage = Matisse.obtainResult(data).get(0);
            imageAvatar.setImageURI(SelectedImage);
        }
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog(getResources().getString(R.string.External_storage),
                            context, Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context, final String permission) {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    private void pushEdit(Uri SelectedImage, Item item) {
        InputStream iStream = null;
        try {
            byte[] image = null;
            if (SelectedImage != null) {
                Context applicationContext = getApplicationContext();
                iStream = applicationContext.getContentResolver().openInputStream(SelectedImage);
                assert iStream != null;
                image = Utility.getBytes(iStream);
            }

//            push(image);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void putImage(String imageUrl , ImageView image){
        GlideUrl url = new GlideUrl(imageUrl, new LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build());
        Glide.with(DetailsActivity.this)
                .asBitmap()
                .load(url)
                .into(image);
    }
}