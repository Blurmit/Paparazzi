package systems.pizza.paparazzi.ui.defined;

import systems.pizza.paparazzi.Paparazzi;
import systems.pizza.paparazzi.clipboard.GenericClipboardOwner;
import systems.pizza.paparazzi.clipboard.TransferableImage;
import systems.pizza.paparazzi.resource.Resources;
import systems.pizza.paparazzi.ui.CustomButton;
import systems.pizza.paparazzi.ui.CustomPanel;
import systems.pizza.paparazzi.util.Debug;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainPanel extends CustomPanel {

    private final BufferedImage image;

    public MainPanel() {
        this.image = null;
    }

    public MainPanel(BufferedImage screenshot) {
        this.image = screenshot;
    }

    @Override
    public void configure(Paparazzi paparazzi) {
        paparazzi.getWindow().setSize(this.image == null ? 550 : Math.max(this.image.getWidth() + 100, 550), this.image == null ? 225 : Math.max(this.image.getHeight() + 100, 225));

        // Initialize all the components.
        final CustomButton captureButton = new CustomButton("Capture");
        final CustomButton copyButton = new CustomButton("Copy");
        final CustomButton delayButton = new CustomButton("Delay");
        final CustomButton uploadButton = new CustomButton("Upload");
        final CustomButton saveButton = new CustomButton("Save");
        final JComboBox<Integer> delays = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5});

        // Load all the images.
        final Image captureButtonIcon = Resources.find("camera.png").getAsImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        final Image delayButtonIcon = Resources.find("stopwatch.png").getAsImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        final Image uploadButtonIcon = Resources.find("upload.png").getAsImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        final Image copyButtonIcon = Resources.find("clipboard.png").getAsImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        final Image saveButtonIcon = Resources.find("save.png").getAsImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);

        // Assign each icon to the desired button.
        captureButton.setIcon(new ImageIcon(captureButtonIcon));
        delayButton.setIcon(new ImageIcon(delayButtonIcon));
        uploadButton.setIcon(new ImageIcon(uploadButtonIcon));
        saveButton.setIcon(new ImageIcon(saveButtonIcon));
        copyButton.setIcon(new ImageIcon(copyButtonIcon));

        // Set the locations.
        captureButton.setLocation(paparazzi.getWindow().getWidth() / 2, paparazzi.getWindow().getHeight() / 2);
        delayButton.setLocation(paparazzi.getWindow().getWidth() / 2, paparazzi.getWindow().getHeight() / 2);
        uploadButton.setLocation(paparazzi.getWindow().getWidth() / 2, paparazzi.getWindow().getHeight() / 2);
        saveButton.setLocation(paparazzi.getWindow().getWidth() / 2, paparazzi.getWindow().getHeight() / 2);
        copyButton.setLocation(paparazzi.getWindow().getWidth() / 2, paparazzi.getWindow().getHeight() / 2);

        // Misc configuration
        captureButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        delayButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        copyButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        uploadButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        copyButton.setEnabled(this.image != null);
        uploadButton.setEnabled(this.image != null);
        saveButton.setEnabled(this.image != null);

        delays.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            if (index == -1) {
                return delayButton;
            }

            final CustomButton button = new CustomButton((value == 1) ? value + " second" : value + " seconds");

            if (isSelected) {
                paparazzi.setScreenshotDelay(value * 1000);
                button.setSelected(true);
            }

            button.setFocusPainted(cellHasFocus);
            return button;
        });
        saveButton.addActionListener(event -> {
            final JFileChooser fileChooser = new JFileChooser();
            final int returnValue = fileChooser.showSaveDialog(paparazzi.getWindow());

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    ImageIO.write(this.image, "png", fileChooser.getSelectedFile());
                } catch (IOException e) {
                    Debug.log(Debug.Level.WARN, "Could not save image as file", e);
                }
            }
        });
        uploadButton.addActionListener(event -> paparazzi.setWindow(new UploadPanel(this.image)));
        captureButton.addActionListener(event -> {
            paparazzi.getWindow().setVisible(false);
            final Timer timer = new Timer(paparazzi.getScreenshotDelay(), callback -> paparazzi.setWindow(new ScreenshotPanel()));
            timer.setRepeats(false);
            timer.start();
        });
        copyButton.addActionListener(event -> Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new TransferableImage(this.image), new GenericClipboardOwner()));

        // Finally, add each button.
        add(captureButton);
        add(delays);
        add(copyButton);
        add(uploadButton);
        add(saveButton);

        if (this.image == null) {
            add(new JLabel("No screenshots yet :("), BorderLayout.CENTER);
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (this.image != null) {
            graphics.drawImage(this.image, 50, 50, this);
        }
    }

    @Override
    public boolean isConstant() {
        return true;
    }

}
