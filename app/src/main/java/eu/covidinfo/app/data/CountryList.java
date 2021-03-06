package eu.covidinfo.app.data;

import androidx.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class CountryList {

    // Singleton instance of CountryList
    private static CountryList singleton;

    // HashMap to hold all of the countries
    private final HashMap<String, String> countryList = new HashMap<>();

    // Return the CountryList singleton instance
    public static CountryList get() {
        if (CountryList.singleton == null) {
            CountryList.singleton = new CountryList();
        }
        return CountryList.singleton;
    }

    // CountryList constructor
    private CountryList() {
        this.getDataFromAPI();
    }

    // Load and parse data from API
    private void getDataFromAPI() {

        // API endpoint URL
        String url = "https://restcountries.eu/rest/v2/all";

        // Run API request
        JsonElement response = new Request(url).get();

        // Parse API response
        if (response != null) {
            JsonArray root = response.getAsJsonArray();
            for (JsonElement item : root) {
                JsonObject country = item.getAsJsonObject();
                String code = country.get("alpha2Code").getAsString();
                String name = country.get("name").getAsString();
                this.countryList.put(code.toLowerCase(), name);
            }
        }
    }

    // Get the country's name by it's code
    @Nullable
    public String get(String code) {
        return this.countryList.get(code);
    }

    // Get the set of country codes
    public Set<String> keys() {
        return Collections.unmodifiableSet(this.countryList.keySet());
    }

    // Get the collection of country names
    public Collection<String> values() {
        return Collections.unmodifiableCollection(this.countryList.values());
    }

}
