package eu.covidinfo.app.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

public class Country {

    // Country information
    private String code;
    private String name;
    private int population;

    // Base statistics
    private int cases;
    private int deaths;
    private int recovered;
    private int critical;

    // Calculated statistics
    private double deathRate;
    private double recoveryRate;
    private double casesPerMillion;

    // Country constructor
    public Country(String countryCode) {
        this.getDataFromAPI(countryCode);
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public int getPopulation() {
        return this.population;
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

    public int getCritical() {
        return this.critical;
    }

    public double getDeathRate() {
        return this.deathRate;
    }

    public double getRecoveryRate() {
        return this.recoveryRate;
    }

    public double getCasesPerMillion() {
        return this.casesPerMillion;
    }

    // Load and parse data from API
    private void getDataFromAPI(String countryCode) {

        // API endpoint URL
        String urlTemplate = "https://corona-api.com/countries/%s";
        String url = String.format(urlTemplate, countryCode);

        // Run API request
        JsonElement response = new Request(url).get();

        // Parse API response
        if (response != null) {
            JsonObject root = response.getAsJsonObject();
            JsonObject data = root.getAsJsonObject("data");
            JsonObject latest = data.getAsJsonObject("latest_data");
            JsonObject calc = latest.getAsJsonObject("calculated");

            this.code = data.get("code").getAsString();
            this.name = data.get("name").getAsString();
            this.population = data.get("population").getAsInt();

            this.cases = latest.get("confirmed").getAsInt();
            this.deaths = latest.get("deaths").getAsInt();
            this.recovered = latest.get("recovered").getAsInt();
            this.critical = latest.get("critical").getAsInt();

            this.deathRate = calc.get("death_rate").getAsDouble();
            this.recoveryRate = calc.get("recovery_rate").getAsDouble();
            this.casesPerMillion = calc.get("cases_per_million_population").getAsDouble();
        }
    }

    @NotNull
    @Override
    public String toString() {
        return "Country { " +
                "name='" + this.name + "'" +
                ", code='" + this.code + "'" +
                ", population=" + this.population +
                ", cases=" + this.cases +
                ", deaths=" + this.deaths +
                ", recovered=" + this.recovered +
                ", critical=" + this.critical +
                ", deathRate=" + this.deathRate +
                ", recoveryRate=" + this.recoveryRate +
                ", casesPerMillion=" + this.casesPerMillion +
                " }";
    }

}
