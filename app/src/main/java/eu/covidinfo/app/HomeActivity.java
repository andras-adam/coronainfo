package eu.covidinfo.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;

import eu.covidinfo.app.data.Global;

public class HomeActivity extends AppCompatActivity {

    Global data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get global data
        data = new Global();

        // Update UI widgets
        this.updateTextViews();
        this.updateProgressBars();

        // Set up listener to navigate to search screen
        this.searchClickListener();
    }

    private void searchClickListener() {
        View view = findViewById(R.id.viewSearch);
        view.setOnClickListener(target -> {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        });
    }

    // Update TextView widgets
    private void updateTextViews() {

        // Get TextView widgets
        TextView totalConfirmed = findViewById(R.id.numberTotalConfirmed);
        TextView totalRecovered = findViewById(R.id.numberTotalRecovered);
        TextView totalDeaths = findViewById(R.id.numberTotalDeaths);
        TextView newConfirmed = findViewById(R.id.numberDeathRate);
        TextView newRecovered = findViewById(R.id.numberRecoveryRate);
        TextView newDeaths = findViewById(R.id.numberCasesPerMillion);

        // Create decimal formatter
        DecimalFormat formatter = new DecimalFormat("#,###");

        // Update TextViews
        totalConfirmed.setText(formatter.format(this.data.getCases()));
        totalRecovered.setText(formatter.format(this.data.getRecovered()));
        totalDeaths.setText(formatter.format(this.data.getDeaths()));
        newConfirmed.setText(formatter.format(this.data.getNewCases()));
        newRecovered.setText(formatter.format(this.data.getNewRecovered()));
        newDeaths.setText(formatter.format(this.data.getNewDeaths()));
    }

    // Update ProgressBar widgets
    private void updateProgressBars() {

        // Get ProgressBar widgets
        ProgressBar confirmedProgress = findViewById(R.id.progressTotalConfirmed);
        ProgressBar recoveredProgress = findViewById(R.id.progressTotalRecovered);
        ProgressBar deathsProgress = findViewById(R.id.progressTotalDeaths);

        // Update ProgressBars
        confirmedProgress.setProgress(100);
        recoveredProgress.setProgress(Math.round(1.0f * this.data.getRecovered() / this.data.getCases() * 100));
        deathsProgress.setProgress(Math.round(1.0f * this.data.getDeaths() / this.data.getCases() * 100));
    }

}
