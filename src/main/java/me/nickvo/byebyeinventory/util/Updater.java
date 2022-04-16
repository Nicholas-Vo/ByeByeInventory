package me.nickvo.byebyeinventory.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class Updater {

    public Updater(Logger log, String currentVersion) {
        final String USER_AGENT = "PictureLogin Plugin";
        final String PLUGIN_ID = "101405"; // This is the Spigot plugin ID

        try {
            // Connect to SpiGet
            URL url = new URL("https://api.spiget.org/v2/resources/" + PLUGIN_ID + "/versions/latest");
            var connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", USER_AGENT);// Set User-Agent

            // Read downloaded file
            var streamReader = new InputStreamReader(connection.getInputStream());
            JsonObject jsonObject = new JsonParser().parse(streamReader).getAsJsonObject();

            String latest_version = jsonObject.get("name").toString().replace("\"", "");

            // Compare current plugin version with downloaded one
            if (!currentVersion.equalsIgnoreCase(latest_version)) {
                log.info("New update available! Current version: " + currentVersion + " latest version: " + latest_version);
                log.info("update_available_download");
            }
        } catch (Exception e) {
            log.warning("Error with the updater: " + e.getMessage());
        }
    }

}
