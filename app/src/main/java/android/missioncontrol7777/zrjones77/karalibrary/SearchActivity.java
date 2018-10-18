package android.missioncontrol7777.zrjones77.karalibrary;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode.TYPE_CALENDAR_EVENT;

public class SearchActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    //ImageView mImageView = (ImageView)findViewById(R.id.bookImageView);
    String mCurrentPhotoPath;
    Bitmap bit;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Activate the Barcode Scanner
        FirebaseVisionBarcodeDetectorOptions options =
                new FirebaseVisionBarcodeDetectorOptions.Builder()
                        .setBarcodeFormats(
                                FirebaseVisionBarcode.FORMAT_QR_CODE,
                                FirebaseVisionBarcode.FORMAT_EAN_13,
                                FirebaseVisionBarcode.FORMAT_ALL_FORMATS)
                        .build();
        try {

            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/pic.jpg");
            String fname = Environment.getExternalStorageDirectory() + "/pic.jpg";
            System.out.println(fname);

            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
                    .getVisionBarcodeDetector();

            Task<List<FirebaseVisionBarcode>> result = detector.detectInImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                            // Task completed successfully
                            // ...
                            final TextView searchTog = (TextView) findViewById(R.id.search_toggle);
                            //searchTog.setText("onSuccess");
                            if(barcodes == null) {
                                searchTog.setText("Not Working");
                            } else {
                                if(barcodes.size() > 0) {
                                    searchTog.setText(barcodes.get(0).getRawValue());
                                }
                            }
                            //searchTog.setText(barcodes.size());
                            //  System.out.println(barcodes.get(0).getRawValue());
                            // searchTog.setText(barcodes.get(0).getRawValue());

                            // String rawValue = barcodes.get(0).getRawValue();

                            // int valueType = barcodes.get(0).getValueType();

                            // searchTog.setText(rawValue + " " + valueType);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...
                            final TextView searchTog = (TextView) findViewById(R.id.search_toggle);
                            searchTog.setText("onFailure");
                        }
                    });

      //      System.out.println(result.getResult());

        } catch(OutOfMemoryError e) {
            System.out.println("Not workign");
        }
    }




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Intent i = new Intent(this, CameraActivity.class);
        //startActivityForResult(i, 1);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
               // ActivityCompat.requestPermissions(this,
                //        new String[]{android.Manifest.permission.READ_CONTACTS},
                //        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        String path = Environment.getExternalStorageDirectory() + "/pic.jpg";
        File file = new File(path);
        Uri outputFileUri = Uri.fromFile(file);
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

        //Took from here for onActivityResult




        Button tvBu1 = (Button) findViewById(R.id.search_btn);

        final TextView searchTog = (TextView) findViewById(R.id.search_toggle);
        Button isbnBtn = (Button) findViewById(R.id.isbn_btn);
        isbnBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchTog.setText("isbn");
            }
        });

        Button authorLastBtn = (Button) findViewById(R.id.author_last_btn);
        authorLastBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchTog.setText("author_last");
            }
        });

        Button titleBtn = (Button) findViewById(R.id.title_btn);
        titleBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchTog.setText("title");
            }
        });

        Button genreBtn = (Button) findViewById(R.id.genre_btn);
        genreBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchTog.setText("genre");
            }
        });

        tvBu1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 EditText searchTxt = (EditText) findViewById(R.id.search_txt);

                 String searchString = searchTxt.getText().toString();
                 String searchToggle = searchTog.getText().toString();

                 Intent i = new Intent(SearchActivity.this, LibraryActivity.class);
                 i.putExtra("key", searchString);
                 i.putExtra("key1", searchToggle);
                 startActivity(i);

            }
        });


    }

}
