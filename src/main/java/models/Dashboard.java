package models;

public class Dashboard {

    private static final String URL = "https://matazamlata.humanity.com/";
    private static final String DASH_URL = URL + "app/dashboard/";

    public static String getUrl() {
        return URL;
    }

    public static String getDashUrl() {
        return DASH_URL;
    }
}
