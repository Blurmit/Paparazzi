package systems.pizza.paparazzi.util;

import java.awt.*;

public class Robots {

    public static Robot create() {
        try {
            return new Robot();
        } catch (Exception e) {
            Debug.log(Debug.Level.ERROR, "Something went wrong whilst creating a new robot instance.", e);
            return null;
        }
    }

}
