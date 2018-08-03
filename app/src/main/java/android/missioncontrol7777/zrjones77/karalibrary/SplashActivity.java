package android.missioncontrol7777.zrjones77.karalibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button beginButton = (Button)findViewById(R.id.enter_btn);
        beginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mInHome = new Intent(getBaseContext(), MenuActivity.class);
                startActivity(mInHome);
            }
        });
    }
}
