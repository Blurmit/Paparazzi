package systems.pizza.paparazzi.util;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class MultiscreenUtil {

    public static Rectangle2D getTotalScreenDimensions() {
        final Rectangle2D result = new Rectangle2D.Double();
        final GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();

        for (GraphicsDevice gd : environment.getScreenDevices()) {
            for (GraphicsConfiguration graphicsConfiguration : gd.getConfigurations()) {
                Rectangle2D.union(result, graphicsConfiguration.getBounds(), result);
            }
        }

        return result;
    }

}
