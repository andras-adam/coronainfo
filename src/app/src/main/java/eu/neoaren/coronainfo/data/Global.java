package eu.neoaren.coronainfo.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

public class Global {

    // Global statistics
    private int cases;
    private int deaths;
    private int recovered;
    private int newCases;
    private int newDeaths;
    private int newRecovered;

    // Global constructor
    public Global() {
        this.getDataFromAPI();
    }

    public int getCases() {
        return this.cases;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public int getRecovered() {
        return this.recovered;
    }

    public int getNewCases() {
        return this.newCases;
    }

    public int getNewDeaths() {
        return this.newDeaths;
    }

    public int getNewRecovered() {
        return this.newRecovered;
    }

    // Load and parse data from API
    private void getDataFromAPI() {

        // API endpoint URL
        String url = "https://corona-api.com/timeline";

        // Run API request
        JsonElement response = new Request(url).get();

        // Parse API response
        if (response != null) {
            JsonObject root = response.getAsJsonObject();
            JsonArray data = root.getAsJsonArray("data");
            JsonObject latest = data.get(0).getAsJsonObject();

            this.cases = latest.get("confirmed").getAsInt();
            this.deaths = latest.get("deaths").getAsInt();
            this.recovered = latest.get("recovered").getAsInt();
            this.newCases = latest.get("new_confirmed").getAsInt();
            this.newDeaths = latest.get("new_recovered").getAsInt();
            this.newRecovered = latest.get("new_deaths").getAsInt();
        }

    }

    @NotNull
    @Override
    public String toString() {
        return "Global { " +
                "cases=" + this.cases +
                ", deaths=" + this.deaths +
                ", recovered=" + this.recovered +
                ", newCases=" + this.newCases +
                ", newDeaths=" + this.newDeaths +
                ", newRecovered=" + this.newRecovered +
                " }";
    }

}
