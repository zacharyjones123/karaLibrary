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

public class SearchActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button tvBu1 = (Button) findViewById(R.id.search_btn);

        tvBu1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 EditText searchTxt = (EditText) findViewById(R.id.search_txt);

                 String searchString = searchTxt.getText().toString();

                 Intent i = new Intent(SearchActivity.this, MainActivity.class);
                 i.putExtra("key", searchString);
                 startActivity(i);

            }
        });


    }

}
