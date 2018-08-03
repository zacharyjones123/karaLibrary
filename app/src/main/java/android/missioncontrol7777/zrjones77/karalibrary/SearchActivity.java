package android.missioncontrol7777.zrjones77.karalibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
