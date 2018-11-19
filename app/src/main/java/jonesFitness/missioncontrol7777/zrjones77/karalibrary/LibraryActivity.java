package jonesFitness.missioncontrol7777.zrjones77.karalibrary;

import android.content.Context;
import android.content.Intent;
import jonesFitness.missioncontrol7777.zrjones77.karalibrary.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LibraryActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static Library library = null;
    public static Context mContext;

    View Custmv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        mContext = this;

        File libraryFile = new File(getFilesDir(), "home");
        library = makeNewLibrary(mContext, libraryFile);


        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_books, null);



        // Find the ScrollView
        ScrollView sv = (ScrollView) v.findViewById(R.id.book_scroll_view);

        //Add layout
        final LinearLayout bookView = new LinearLayout(this);
        bookView.setOrientation(LinearLayout.VERTICAL);

        //Add in Search Bar Layout
        LinearLayout searchView = new LinearLayout(this);
        searchView.setOrientation(LinearLayout.HORIZONTAL);

        Button tvBu = new Button(this);
        tvBu.setText("Search");
        tvBu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mInHome = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(mInHome);
            }
        });

        //This is for if the search is done
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String value = extras.getString("key");
            String searchKey = extras.getString("key1");


            //Making the cards (by search)

            //color switcher
            int switcher = 0;

            if(searchKey.equals("title")) {
                //Search by title
                System.out.println(library.getBooks().size());
                for(Book b : library.getBooks()) {
                    if (b.getTitle().toLowerCase().contains(value.toLowerCase())) {
                        LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View template = inflater1.inflate(R.layout.layout_book_template, null);

                        //System.out.println(b);
                        CardView cardView1 = new CardView(this);
                        if (switcher == 1) {
                            cardView1.setCardBackgroundColor(getResources().getColor(R.color.color_book_card2));
                            switcher = 0;
                        } else {
                            cardView1.setCardBackgroundColor(getResources().getColor(R.color.color_book_card1));
                            switcher = 1;
                        }
                        RelativeLayout realView = new RelativeLayout(this);

                        ImageView imageView = (ImageView) template.findViewById(R.id.bookImageView);
                        //Need to find the right resource
                        int checkExistence = LibraryActivity.this.getResources().getIdentifier("i" + b.getISBN(), "drawable", LibraryActivity.this.getPackageName());

                        if (checkExistence != 0) {  // the resouce exists...
                            //System.out.println("Worked");
                            imageView.setImageResource(checkExistence);
                        } else if(b.getHasImage()) {
                            resizeImage(Environment.getExternalStorageDirectory() + "/i" + b.getISBN() + ".jpg");
                            imageView.setImageURI(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/i" + b.getISBN() + ".jpg")));
                        }else {
                            imageView.setImageResource(R.drawable.i0000000000000);
                        }
                        TextView textViewBookTitle = (TextView) template.findViewById(R.id.textViewBookTitle);
                        textViewBookTitle.setText(b.getTitle());
                        TextView textViewAuthor = (TextView) template.findViewById(R.id.textViewAuthor);
                        textViewAuthor.setText(b.getAuthorFirst() + " " + b.getAuthorLast());
                        TextView textViewRating = (TextView) template.findViewById(R.id.textViewRating);
                        textViewRating.setText("5");
                        TextView textViewISBN = (TextView) template.findViewById(R.id.textViewISBN);
                        textViewISBN.setText(b.getISBN());

                        realView.addView(template);
                        cardView1.addView(realView);
                        bookView.addView(cardView1);
                    }
                }
                sv.addView(bookView);
                setContentView(v);
            } else if(searchKey.equals("author_last")) {
                //Search by author last
                for(Book b : library.getBooks()) {
                    if (b.getAuthorLast().toLowerCase().contains(value.toLowerCase())) {
                        LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View template = inflater1.inflate(R.layout.layout_book_template, null);

                        //System.out.println(b);
                        CardView cardView1 = new CardView(this);
                        if (switcher == 1) {
                            cardView1.setCardBackgroundColor(getResources().getColor(R.color.color_book_card2));
                            switcher = 0;
                        } else {
                            cardView1.setCardBackgroundColor(getResources().getColor(R.color.color_book_card1));
                            switcher = 1;
                        }
                        RelativeLayout realView = new RelativeLayout(this);

                        ImageView imageView = (ImageView) template.findViewById(R.id.bookImageView);
                        //Need to find the right resource
                        int checkExistence = LibraryActivity.this.getResources().getIdentifier("i" + b.getISBN(), "drawable", LibraryActivity.this.getPackageName());
                        //System.out.println("i" + b.getISBN() + ".jpg");
                        if (checkExistence != 0) {  // the resouce exists...
                            //System.out.println("Worked");
                            imageView.setImageResource(checkExistence);
                        } else if(b.getHasImage()) {
                            resizeImage(Environment.getExternalStorageDirectory() + "/i" + b.getISBN() + ".jpg");
                            imageView.setImageURI(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/i" + b.getISBN() + ".jpg")));
                        }else {
                            imageView.setImageResource(R.drawable.i0000000000000);
                        }
                        TextView textViewBookTitle = (TextView) template.findViewById(R.id.textViewBookTitle);
                        textViewBookTitle.setText(b.getTitle());
                        TextView textViewAuthor = (TextView) template.findViewById(R.id.textViewAuthor);
                        textViewAuthor.setText(b.getAuthorFirst() + " " + b.getAuthorLast());
                        TextView textViewRating = (TextView) template.findViewById(R.id.textViewRating);
                        textViewRating.setText("5");
                        TextView textViewISBN = (TextView) template.findViewById(R.id.textViewISBN);
                        textViewISBN.setText(b.getISBN());

                        realView.addView(template);
                        cardView1.addView(realView);
                        bookView.addView(cardView1);
                    }
                }
                sv.addView(bookView);
                setContentView(v);
            } else if(searchKey.equals("genre")) {
                //Search by Genre
                for(Book b : library.getBooks()) {
                    if (b.getGenre().toLowerCase().equals(value.toLowerCase())) {
                        LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View template = inflater1.inflate(R.layout.layout_book_template, null);

                        //System.out.println(b);
                        CardView cardView1 = new CardView(this);
                        if (switcher == 1) {
                            cardView1.setCardBackgroundColor(getResources().getColor(R.color.color_book_card2));
                            switcher = 0;
                        } else {
                            cardView1.setCardBackgroundColor(getResources().getColor(R.color.color_book_card1));
                            switcher = 1;
                        }
                        RelativeLayout realView = new RelativeLayout(this);

                        ImageView imageView = (ImageView) template.findViewById(R.id.bookImageView);
                        //Need to find the right resource
                        int checkExistence = LibraryActivity.this.getResources().getIdentifier("i" + b.getISBN(), "drawable", LibraryActivity.this.getPackageName());
                        //System.out.println("i" + b.getISBN() + ".jpg");
                        if (checkExistence != 0) {  // the resouce exists...
                            //System.out.println("Worked");
                            imageView.setImageResource(checkExistence);
                        } else if(b.getHasImage()) {
                            resizeImage(Environment.getExternalStorageDirectory() + "/i" + b.getISBN() + ".jpg");
                            imageView.setImageURI(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/i" + b.getISBN() + ".jpg")));
                        }else {
                            imageView.setImageResource(R.drawable.i0000000000000);
                        }
                        TextView textViewBookTitle = (TextView) template.findViewById(R.id.textViewBookTitle);
                        textViewBookTitle.setText(b.getTitle());
                        TextView textViewAuthor = (TextView) template.findViewById(R.id.textViewAuthor);
                        textViewAuthor.setText(b.getAuthorFirst() + " " + b.getAuthorLast());
                        TextView textViewRating = (TextView) template.findViewById(R.id.textViewRating);
                        textViewRating.setText("5");
                        TextView textViewISBN = (TextView) template.findViewById(R.id.textViewISBN);
                        textViewISBN.setText(b.getISBN());

                        realView.addView(template);
                        cardView1.addView(realView);
                        bookView.addView(cardView1);
                    }
                }
                sv.addView(bookView);
                setContentView(v);
            } else if(searchKey.equals("isbn")) {
                //Search by ISBN
                for(Book b : library.getBooks()) {
                    if (b.getISBN().contains(value)) {
                        LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View template = inflater1.inflate(R.layout.layout_book_template, null);

                        //System.out.println(b);
                        CardView cardView1 = new CardView(this);
                        if (switcher == 1) {
                            cardView1.setCardBackgroundColor(getResources().getColor(R.color.color_book_card2));
                            switcher = 0;
                        } else {
                            cardView1.setCardBackgroundColor(getResources().getColor(R.color.color_book_card1));
                            switcher = 1;
                        }
                        RelativeLayout realView = new RelativeLayout(this);

                        ImageView imageView = (ImageView) template.findViewById(R.id.bookImageView);
                        //Need to find the right resource
                        int checkExistence = LibraryActivity.this.getResources().getIdentifier("i" + b.getISBN(), "drawable", LibraryActivity.this.getPackageName());
                        //System.out.println("i" + b.getISBN() + ".jpg");
                        if (checkExistence != 0) {  // the resouce exists...
                            //System.out.println("Worked");
                            imageView.setImageResource(checkExistence);
                        } else if(b.getHasImage()) {
                            resizeImage(Environment.getExternalStorageDirectory() + "/i" + b.getISBN() + ".jpg");
                            imageView.setImageURI(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/i" + b.getISBN() + ".jpg")));
                        }else {
                            imageView.setImageResource(R.drawable.i0000000000000);
                        }
                        TextView textViewBookTitle = (TextView) template.findViewById(R.id.textViewBookTitle);
                        textViewBookTitle.setText(b.getTitle());
                        TextView textViewAuthor = (TextView) template.findViewById(R.id.textViewAuthor);
                        textViewAuthor.setText(b.getAuthorFirst() + " " + b.getAuthorLast());
                        TextView textViewRating = (TextView) template.findViewById(R.id.textViewRating);
                        textViewRating.setText("5");
                        TextView textViewISBN = (TextView) template.findViewById(R.id.textViewISBN);
                        textViewISBN.setText(b.getISBN());

                        realView.addView(template);
                        cardView1.addView(realView);
                        bookView.addView(cardView1);
                    }
                }
                sv.addView(bookView);
                setContentView(v);
            } else {
                //Does Not Work
            }


        } else {

            bookView.addView(tvBu);

            //Making the cards

            //color switcher
            int switcher = 0;


            for (Book b : library.getBooks()) {
                LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View template = inflater1.inflate(R.layout.layout_book_template, null);

                //System.out.println(b);
                CardView cardView1 = new CardView(this);
                if (switcher == 1) {
                    cardView1.setCardBackgroundColor(getResources().getColor(R.color.color_book_card2));
                    switcher = 0;
                } else {
                    cardView1.setCardBackgroundColor(getResources().getColor(R.color.color_book_card1));
                    switcher = 1;
                }
                RelativeLayout realView = new RelativeLayout(this);

                ImageView imageView = (ImageView) template.findViewById(R.id.bookImageView);
                //Need to find the right resource
                int checkExistence = LibraryActivity.this.getResources().getIdentifier("i" + b.getISBN(), "drawable", LibraryActivity.this.getPackageName());
                //System.out.println("i" + b.getISBN() + ".jpg");
                if (checkExistence != 0) {  // the resouce exists...
                    //System.out.println("Worked");
                    imageView.setImageResource(checkExistence);
                } else if(b.getHasImage()) {
                    resizeImage(Environment.getExternalStorageDirectory() + "/i" + b.getISBN() + ".jpg");
                    imageView.setImageURI(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/i" + b.getISBN() + ".jpg")));
                }else {
                    imageView.setImageResource(R.drawable.i0000000000000);
                }
                TextView textViewBookTitle = (TextView) template.findViewById(R.id.textViewBookTitle);
                textViewBookTitle.setText(b.getTitle());
                TextView textViewAuthor = (TextView) template.findViewById(R.id.textViewAuthor);
                textViewAuthor.setText(b.getAuthorFirst() + " " + b.getAuthorLast());
                TextView textViewRating = (TextView) template.findViewById(R.id.textViewRating);
                textViewRating.setText("5");
                TextView textViewISBN = (TextView) template.findViewById(R.id.textViewISBN);
                textViewISBN.setText(b.getISBN());

                realView.addView(template);
                cardView1.addView(realView);
                bookView.addView(cardView1);
            }

            sv.addView(bookView);

            setContentView(v);

        }
    }

    /**
     * This is the method I used to add all of the parts to the
     */
    protected void makeNewDatabase()  {
        InputStream is = this.getResources().openRawResource(R.raw.isbn);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String data = "";

        Library library = new Library();

        if(is != null) {
            try {
                while ((data = reader.readLine()) != null) {
                    Book book = new Book();
                    book.setISBN(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setTitle(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setAuthorFirst(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setAuthorLast(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setGenre(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setGrade(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setSubject(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setDescription(data);

                    library.addBook(book);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference bookRef = database.getReference(book.getTitle());

                    bookRef.child("ISBN").setValue(book.getISBN());
                    bookRef.child("Title").setValue(book.getTitle());
                    bookRef.child("AuthorFirst").setValue(book.getAuthorFirst());
                    bookRef.child("AuthorLast").setValue(book.getAuthorLast());
                    bookRef.child("Genre").setValue(book.getGenre());
                    bookRef.child("Grade").setValue(book.getGrade());
                    bookRef.child("Subject").setValue(book.getSubject());
                    bookRef.child("Description").setValue(book.getDescription());
                    //System.out.println(data);
                    //System.out.println("---------------------------");
                    //books.add(new Book(in.next(),in.next(),in.next(), in.next(), in.next(), in.next(), in.next(), in.next()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public static Library makeNewLibrary(Context c, File currLib) {
        InputStream is = c.getResources().openRawResource(R.raw.isbn);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String data = "";

        Library library = new Library();

        if(currLib.exists()) {
            try {
                reader = new BufferedReader(new FileReader(currLib));

                while ((data = reader.readLine()) != null) {
                    Book book = new Book();
                    book.setISBN(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setTitle(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setAuthorFirst(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setAuthorLast(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setGenre(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setGrade(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setSubject(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setDescription(data);

                    library.addBook(book);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(is != null) {
            try {
                while ((data = reader.readLine()) != null) {
                    Book book = new Book();
                    book.setISBN(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setTitle(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setAuthorFirst(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setAuthorLast(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setGenre(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setGrade(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setSubject(data);
                    //System.out.println(data);
                    data = reader.readLine();
                    book.setDescription(data);

                    library.addBook(book);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return library;
    }

    public void resizeImage(String path) {
        Bitmap photo = BitmapFactory.decodeFile(path);

        photo = Bitmap.createScaledBitmap(photo, 100, 100, false);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

        try {
            File f = new File(path);
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
