package systems.pizza.paparazzi;

import java.net.MalformedURLException;
import java.net.URL;

public final class Constants {

    public static URL BASE_URL;
    public static URL UPLOAD_URL;

    static {
        try {
            BASE_URL = new URL("https://images.pizza.systems/");
            UPLOAD_URL = new URL(BASE_URL + "upload");
        } catch (MalformedURLException e) {
            // This will never happen.
            BASE_URL = null;
            UPLOAD_URL = null;
        }
    }

}
