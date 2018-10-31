package jonesFitness.missioncontrol7777.zrjones77.karalibrary;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import jonesFitness.missioncontrol7777.zrjones77.karalibrary.R;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
    Context cAdd;
    public static Bitmap myBitmap;
    public static String isbnShare;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);
        cAdd = this;

        TextView isbn_text = (TextView)findViewById(R.id.isbn_json_text);
        isbn_text.setVisibility(View.GONE);
        final EditText isbn_edit = (EditText)findViewById(R.id.isbn_edit);
        isbn_edit.setVisibility(View.VISIBLE);
        //isbn_edit.setText("isbn");

        TextView author_view = (TextView)findViewById(R.id.author_json_text);
        author_view.setVisibility(View.GONE);
        final EditText author_edit = (EditText)findViewById(R.id.author_edit);
        author_edit.setVisibility(View.VISIBLE);
        //author_edit.setText("author");

        TextView title_text = (TextView)findViewById(R.id.title_json_text);
        title_text.setVisibility(View.GONE);
        final EditText title_edit = (EditText)findViewById(R.id.title_edit);
        title_edit.setVisibility(View.VISIBLE);
        //title_edit.setText("title");

        TextView categories_text = (TextView)findViewById(R.id.categories_json_text);
        categories_text.setVisibility(View.GONE);
        final EditText categories_edit = (EditText)findViewById(R.id.categories_edit);
        categories_edit.setVisibility(View.VISIBLE);
        //categories_edit.setText("home");

        TextView description_text = (TextView)findViewById(R.id.description_json_text);
        description_text.setVisibility(View.GONE);
        final EditText description_edit = (EditText)findViewById(R.id.description_edit);
        description_edit.setVisibility(View.VISIBLE);
        //description_edit.setText("description");

        final Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String ISBN = isbn_edit.getText().toString();
                String title = title_edit.getText().toString();
                String author = author_edit.getText().toString();
                String[] splitter = author.split(" ");
                String authorFirst = splitter[0];
                String authorLast = splitter[1];
                String genre = categories_edit.getText().toString();
                String grade = "Nah";
                String subject = categories_edit.getText().toString();
                String description = description_edit.getText().toString();


                if(LibraryActivity.library == null) {
                    LibraryActivity.library = LibraryActivity.makeNewLibrary(cAdd);
                }
                //I need to add a book to the library
                LibraryActivity.library.addBook(new Book(ISBN, title,
                        authorFirst, authorLast,
                        genre, grade,
                        subject,description));
                //this will write it out
                LibraryActivity.library.reWriteLibrary();

            }
        });

        final Button cameraButton = findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    //This is for the Google API
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());

                    //Need to check permission
                    if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        String path = Environment.getExternalStorageDirectory() + "/i" + isbn_edit.getText().toString() + ".jpg";
                        isbnShare = path;
                        File file = new File(path);
                        Uri outputFileUri = Uri.fromFile(file);
                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                        if (isbn_edit.getText().toString().equals(null)) {
                            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                        } else {
                            startActivity(intent);
                        }
                    } else {
                        if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                            Toast.makeText(getApplicationContext(), "Camera permission is needed to show the camera preview.", Toast.LENGTH_SHORT).show();
                        }

                        //Request Camera permission
                        requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 1);

                    }
                }
        });

        final Button updatePicButton = findViewById(R.id.update_button);
        updatePicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/i" + isbn_edit.getText().toString() + ".jpg");
                //String fname = Environment.getExternalStorageDirectory() + "/pic.jpg";
                //System.out.println(fname);
                BitmapDrawable ob = new BitmapDrawable(getResources(), myBitmap);
                ScrollView layout = (ScrollView)findViewById(R.id.scroll_view);
                layout.setBackgroundDrawable(ob);

            }
        });



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
                                        TextView title_text = (TextView)findViewById(R.id.title_json_text);
                                        title_text.setText("Does Not Exist");
                                    }
                                } else {
                                    TextView title_text = (TextView)findViewById(R.id.title_json_text);
                                    title_text.setText("No Barcode");
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
