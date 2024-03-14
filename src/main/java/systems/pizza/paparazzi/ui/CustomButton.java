package systems.pizza.paparazzi.ui;

import javax.swing.*;
import java.beans.ConstructorProperties;

public class CustomButton extends JButton {

    /**
     * Creates a button with no set text or icon.
     */
    public CustomButton() {
        this(null, null);
    }

    /**
     * Creates a button with an icon.
     *
     * @param icon the Icon image to display on the button
     */
    public CustomButton(Icon icon) {
        this(null, icon);
    }

    /**
     * Creates a button with text.
     *
     * @param text the text of the button
     */
    @ConstructorProperties({"text"})
    public CustomButton(String text) {
        this(text, null);
    }

    /**
     * Creates a button where properties are taken from the
     * <code>Action</code> supplied.
     *
     * @param a the <code>Action</code> used to specify the new button
     * @since 1.3
     */
    public CustomButton(Action a) {
        super(a);
    }

    /**
     * Creates a button with initial text and an icon.
     *
     * @param text the text of the button
     * @param icon the Icon image to display on the button
     */
    public CustomButton(String text, Icon icon) {
        super(text, icon);
    }

    public void addActionListener(CustomActionListener actionListener) {
        super.addActionListener(actionListener);
    }

}
