package systems.pizza.paparazzi;

import systems.pizza.paparazzi.util.Debug;
import com.formdev.flatlaf.*;

import javax.swing.*;

public class Bootstrap {

    public static void main(String... args) {
        Debug.log("Welcome to Paparazzi. We will begin our platform setup now.");
        FlatDarkLaf.setup();
        JFrame.setDefaultLookAndFeelDecorated(true);

        final Paparazzi paparazzi = new Paparazzi();

        try {
            paparazzi.launch();
        } catch (Throwable t) {
            Debug.log(Debug.Level.ERROR, "Something went wrong during the execution of Paparazzi", t);
            System.exit(1);
        }

        Runtime.getRuntime().addShutdownHook(new Thread("Shutdown Thread") {
            @Override
            public void run() {
                Debug.log(Debug.Level.INFO, "Shutdown thread was invoked.");
                paparazzi.shutdown();
            }
        });
    }

}