package eu.covidinfo.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import eu.covidinfo.app.data.CountryList;

public class SearchActivity extends AppCompatActivity {

    public static final String EXTRA_COUNTRY_CODE = "eu.covidinfo.app.EXTRA_COUNTRY_CODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get country codes and names
        List<String> countryCodes = CountryList.getInstance().keys();
        List<String> countryNames = CountryList.getInstance().values();

        // Render country list
        ListView lv = findViewById(R.id.listCountries);
        lv.setAdapter(new ArrayAdapter<>(this, R.layout.adapter_country, countryNames));
        lv.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            Intent intent = new Intent(this, CountryActivity.class);
            intent.putExtra(EXTRA_COUNTRY_CODE, countryCodes.get(i));
            startActivity(intent);
        });
    }

}
