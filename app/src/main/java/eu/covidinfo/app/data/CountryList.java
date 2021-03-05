package eu.covidinfo.app.data;

import androidx.annotation.Nullable;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class CountryList {

    // Singleton instance of CountryList
    private static final CountryList singleton = new CountryList();

    // HashMap to hold all of the countries
    private final HashMap<String, String> countryList = new HashMap<>();

    // API response type
    private static final TypeToken<ArrayList<HashMap<String, String>>> RESPONSE_TYPE = new TypeToken<ArrayList<HashMap<String, String>>>(){};

    // Return the CountryList singleton instance
    public static CountryList get() {
        return CountryList.singleton;
    }

    // CountryList constructor
    private CountryList() {
        this.fetchCountryList();
    }

    // Fetch the list of countries from API
    private void fetchCountryList() {
        ArrayList<HashMap<String, String>> response = new Request("https://api.covid19api.com/countries").getAs(RESPONSE_TYPE);
        for (HashMap<String, String> map : response) {
            String country = map.get("Country");
            String slug = map.get("Slug");
            if (slug != null && country != null) {
                this.countryList.put(slug.toLowerCase(), country.toLowerCase());
            }
        }
    }

    // Get the country's name by it's slug
    @Nullable
    public String getBySlug(String slug) {
        return this.countryList.get(slug);
    }

    // Get the set of country slugs
    public Set<String> slugs() {
        return Collections.unmodifiableSet(this.countryList.keySet());
    }

}
