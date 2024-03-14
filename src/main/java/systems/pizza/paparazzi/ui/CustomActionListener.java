package systems.pizza.paparazzi.ui;

import systems.pizza.paparazzi.util.Debug;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@FunctionalInterface
public interface CustomActionListener extends ActionListener {

    void execute(ActionEvent event);

    /**
     * Invoked when an action occurs.
     */
    default void actionPerformed(ActionEvent e) {
        try {
            execute(e);
        } catch (Throwable t) {
            Debug.log(Debug.Level.WARN, "Something went wrong whilst ticking the application UI", t);
        }
    }

}
