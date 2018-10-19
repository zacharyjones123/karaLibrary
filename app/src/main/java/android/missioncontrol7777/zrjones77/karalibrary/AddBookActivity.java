package android.missioncontrol7777.zrjones77.karalibrary;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class AddBookActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);

        //This is for the Google API
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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
    }

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

                                    GoogleApiRequest g = new GoogleApiRequest();
                                    g.setContext(getBaseContext());
                                    String s = barcodes.get(0).getRawValue();
                                    System.out.println(s);
                                    JSONObject responseJson = g.doInBackground((String)(barcodes.get(0).getRawValue()));

                                    try {
                                        TextView isbn_text = (TextView)findViewById(R.id.isbn_json_text);
                                        isbn_text.setText((String)responseJson.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("industryIdentifiers").getJSONObject(1).get("identifier"));
                                        TextView author_view = (TextView)findViewById(R.id.author_json_text);
                                        author_view.setText((String)responseJson.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("authors").get(0));
                                        TextView title_text = (TextView)findViewById(R.id.title_json_text);
                                        title_text.setText((String)responseJson.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo").get("title"));
                                        TextView categories_text = (TextView)findViewById(R.id.categories_json_text);
                                        categories_text.setText((String)(String)responseJson.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("categories").get(0));
                                        TextView description_text = (TextView)findViewById(R.id.description_json_text);
                                        description_text.setText((String)(String)responseJson.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo").get("description"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...
                        }
                    });

            //      System.out.println(result.getResult());

        } catch(OutOfMemoryError e) {
            System.out.println("Not workign");
        }
    }
}
