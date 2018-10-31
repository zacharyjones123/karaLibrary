package jonesFitness.missioncontrol7777.zrjones77.karalibrary;

import android.content.Intent;
import jonesFitness.missioncontrol7777.zrjones77.karalibrary.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            //String value = extras.getString("key");
            //System.out.println(value);
        }

        //The Buttons
        Button libraryBtn = (Button)findViewById(R.id.library_btn);
        libraryBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mInHome = new Intent(getBaseContext(), LibraryActivity.class);
                startActivity(mInHome);
            }
        });

        Button studentBtn = (Button)findViewById(R.id.student_btn);
        studentBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mInHome = new Intent(getBaseContext(), StudentActivity.class);
                startActivity(mInHome);
            }
        });

        Button addBtn = (Button)findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mInHome = new Intent(getBaseContext(), AddBookActivity.class);
                startActivity(mInHome);
            }
        });

        //This is done
        Button searchBtn = (Button)findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mInHome = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(mInHome);
            }
        });



    }


}
