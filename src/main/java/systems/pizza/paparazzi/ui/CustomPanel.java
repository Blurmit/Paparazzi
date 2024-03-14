package systems.pizza.paparazzi.ui;

import systems.pizza.paparazzi.Paparazzi;

import javax.swing.*;

public abstract class CustomPanel extends JPanel {

    public abstract void configure(Paparazzi paparazzi);

    public boolean isConstant() {
        return false;
    }

}
