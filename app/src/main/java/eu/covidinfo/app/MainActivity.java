package eu.covidinfo.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Show splash screen for a seconds
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }, 1000);
    }

}
