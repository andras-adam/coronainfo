package eu.covidinfo.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;

import eu.covidinfo.app.data.Country;

public class CountryActivity extends AppCompatActivity {

    Country data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        // Get countryCode from the intent
        Intent intent = getIntent();
        String countryCode = intent.getStringExtra(MainActivity.EXTRA_COUNTRY_CODE);

        // Get country data
        data = new Country(countryCode);

        // Update UI widgets
        this.updateTextViews();
        this.updateProgressBars();
    }

    // Format numbers into display-ready numbers
    private String format(int number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }

    private String format(float number) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return formatter.format(number);
    }

    // Update TextView widgets
    private void updateTextViews() {

        // Get TextView widgets
        TextView countryName = findViewById(R.id.textScreenTitle);
        TextView totalConfirmed = findViewById(R.id.numberTotalConfirmed);
        TextView totalRecovered = findViewById(R.id.numberTotalRecovered);
        TextView totalDeaths = findViewById(R.id.numberTotalDeaths);
        TextView totalCritical = findViewById(R.id.numberTotalCritical);
        TextView deathRate = findViewById(R.id.numberDeathRate);
        TextView recoveryRate = findViewById(R.id.numberRecoveryRate);
        TextView casesPerMillion = findViewById(R.id.numberCasesPerMillion);
        TextView population = findViewById(R.id.numberPopulation);

        // Update TextViews
        countryName.setText(String.format("%s (%s)", this.data.getName(), this.data.getCode()));
        totalConfirmed.setText(this.format(this.data.getCases()));
        totalRecovered.setText(this.format(this.data.getRecovered()));
        totalDeaths.setText(this.format(this.data.getDeaths()));
        totalCritical.setText(this.format(this.data.getCritical()));
        deathRate.setText(this.format(this.data.getDeathRate()));
        recoveryRate.setText(this.format(this.data.getRecoveryRate()));
        casesPerMillion.setText(this.format(this.data.getCasesPerMillion()));
        population.setText(this.format(this.data.getPopulation()));
    }

    // Update ProgressBar widgets
    private void updateProgressBars() {

        // Get ProgressBar widgets
        ProgressBar confirmedProgress = findViewById(R.id.progressTotalConfirmed);
        ProgressBar recoveredProgress = findViewById(R.id.progressTotalRecovered);
        ProgressBar deathsProgress = findViewById(R.id.progressTotalDeaths);
        ProgressBar criticalProgress = findViewById(R.id.progressTotalCritical);

        // Update ProgressBars
        confirmedProgress.setProgress(100);
        recoveredProgress.setProgress(Math.round(1.0f * this.data.getRecovered() / this.data.getCases() * 100));
        deathsProgress.setProgress(Math.round(1.0f * this.data.getDeaths() / this.data.getCases() * 100));
        criticalProgress.setProgress(Math.round(1.0f * this.data.getCritical() / this.data.getCases() * 100));
    }

}
