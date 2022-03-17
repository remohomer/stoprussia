package pl.com.stoprussia.core.websiteloader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class CheckVersion {

    private String lastVersion;
    private final boolean fileExists;

    public CheckVersion() {

        URL url = null;
        BufferedReader in = null;
        lastVersion = "";

        try {
            url = new URL("https://stoprussia.com.pl/download/version.txt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            lastVersion = "error";
        }

        fileExists = exists(Objects.requireNonNull(url).toString());

        try {
            in = new BufferedReader(
                    new InputStreamReader(Objects.requireNonNull(url).openStream()));
        } catch (Exception e) {
            e.printStackTrace();
            lastVersion = "error";
        }

        try {
            lastVersion = Objects.requireNonNull(in).readLine();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            lastVersion = "error";
        }

    }

    public boolean exists(String URLName) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con =
                    (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getLastVersion() {
        return lastVersion;
    }

    public boolean isFileExists() {
        return fileExists;
    }
}

