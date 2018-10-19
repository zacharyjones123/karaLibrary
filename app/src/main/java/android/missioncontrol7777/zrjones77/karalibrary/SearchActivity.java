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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
