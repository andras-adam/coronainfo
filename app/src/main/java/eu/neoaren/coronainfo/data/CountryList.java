package eu.neoaren.coronainfo.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CountryList {

    // Singleton instance of CountryList
    private static CountryList singleton;

    // HashMap to hold all of the countries
    private final List<String> codes = new ArrayList<>();
    private final List<String> names = new ArrayList<>();

    // Return the CountryList singleton instance
    public static CountryList getInstance() {
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
        String url = "https://corona-api.com/countries";

        // Run API request
        JsonElement response = new Request(url).get();

        // Parse API response
        if (response != null) {

            // Extract useful data from response
            JsonObject root = response.getAsJsonObject();
            JsonArray data = root.getAsJsonArray("data");
            List<Map.Entry<String, String>> list = new ArrayList<>();
            for (JsonElement item : data) {
                JsonObject country = item.getAsJsonObject();
                String name = country.get("name").getAsString();
                String code = country.get("code").getAsString();
                list.add(new AbstractMap.SimpleEntry<>(code, name));
            }

            // Sort data by country name
            list.sort((s0, s1) -> s0.getValue().compareToIgnoreCase(s1.getValue()));
            list.forEach(item -> {
                this.codes.add(item.getKey());
                this.names.add(item.getValue());
            });
        }
    }

    // Get the list of country codes
    public List<String> keys() {
        return this.codes;
    }

    // Get the list of country names
    public List<String> values() {
        return this.names;
    }

}
