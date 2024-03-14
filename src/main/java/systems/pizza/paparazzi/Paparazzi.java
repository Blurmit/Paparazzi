package systems.pizza.paparazzi;

import systems.pizza.paparazzi.resource.Resources;
import systems.pizza.paparazzi.ui.CustomPanel;
import systems.pizza.paparazzi.ui.defined.MainPanel;
import systems.pizza.paparazzi.util.Debug;
import systems.pizza.paparazzi.util.ReflectionUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Paparazzi {

    private static Paparazzi INSTANCE;

    public static Paparazzi getInstance() {
        return INSTANCE;
    }

    private final JFrame window = new JFrame("Paparazzi");
    private Point previousWindowLocation = null;
    private int screenshotDelay = 0;

    public void launch() {
        INSTANCE = this;

        Debug.log("Initializing default window settings.");
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setLocationByPlatform(true);
        this.window.setLocationRelativeTo(null);
        this.window.setResizable(false);
        this.window.setIconImage(Resources.find("logo.png").getAsImage());
        this.previousWindowLocation = this.window.getLocation();

        ReflectionUtil.consumeInstance("systems.pizza.paparazzi.ui.defined", Bootstrap.class.getClassLoader(), CustomPanel.class, panel -> {
            if (!panel.isConstant()) {
                return;
            }

            Debug.log("Found constant panel " + panel.getClass().getName() + " - loading it.");
            panel.configure(this);
            this.window.add(panel);
        }, true);

        this.window.setVisible(true);
    }

    public void shutdown() {
        INSTANCE = null;
    }

    public void setWindow(final CustomPanel panel) {
        if (this.window.getContentPane() instanceof MainPanel) {
            this.previousWindowLocation = this.window.getLocation();
        }

        this.window.dispose();
        this.window.setUndecorated(false);
        this.window.setLocation(this.previousWindowLocation);
        this.window.setAlwaysOnTop(false);
        this.window.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        for (WindowFocusListener listener : this.window.getWindowFocusListeners()) {
            this.window.removeWindowFocusListener(listener);
        }

        for (WindowStateListener listener : this.window.getWindowStateListeners()) {
            this.window.removeWindowStateListener(listener);
        }

        for (KeyListener listener : this.window.getKeyListeners()) {
            this.window.removeKeyListener(listener);
        }

        for (MouseListener listener : this.window.getMouseListeners()) {
            this.window.removeMouseListener(listener);
        }

        for (ContainerListener listener : this.window.getContainerListeners()) {
            this.window.removeContainerListener(listener);
        }

        for (MouseMotionListener listener : this.window.getMouseMotionListeners()) {
            this.window.removeMouseMotionListener(listener);
        }

        for (MouseWheelListener listener : this.window.getMouseWheelListeners()) {
            this.window.removeMouseWheelListener(listener);
        }

        panel.configure(this);
        this.window.setContentPane(panel);
        this.window.setVisible(true);
    }

    public Point getPreviousWindowLocation() {
        return this.previousWindowLocation;
    }

    public int getScreenshotDelay() {
        return this.screenshotDelay;
    }

    public void setScreenshotDelay(int delay) {
        this.screenshotDelay = delay;
    }

    public JFrame getWindow() {
        return this.window;
    }

}
