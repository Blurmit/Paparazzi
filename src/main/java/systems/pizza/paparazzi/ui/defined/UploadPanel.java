package systems.pizza.paparazzi.ui.defined;

import systems.pizza.paparazzi.Paparazzi;
import systems.pizza.paparazzi.clipboard.GenericClipboardOwner;
import systems.pizza.paparazzi.clipboard.TransferableString;
import systems.pizza.paparazzi.resource.Resources;
import systems.pizza.paparazzi.ui.CustomButton;
import systems.pizza.paparazzi.ui.CustomPanel;
import systems.pizza.paparazzi.util.Debug;
import systems.pizza.paparazzi.util.FileSharing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Executors;

public class UploadPanel extends CustomPanel {

    private final BufferedImage image;
    private final JProgressBar uploadProgress = new JProgressBar();

    public UploadPanel(final BufferedImage image) {
        this.image = image;
    }

    @Override
    public void configure(Paparazzi paparazzi) {
        paparazzi.getWindow().setSize(300, 75);

        this.uploadProgress.setIndeterminate(true);
        add(this.uploadProgress);

        Executors.newSingleThreadExecutor().submit(() -> {
            final String link;

            try {
                link = FileSharing.upload(this.image);
            } catch (IOException e) {
                Debug.log(Debug.Level.WARN, "Failed to share image file", e);
                paparazzi.setWindow(new MainPanel());
                return;
            }

            remove(this.uploadProgress);
            final CustomButton copyButton = new CustomButton("Copy");
            final CustomButton homeButton = new CustomButton("Home");
            final CustomButton cancelButton = new CustomButton("Cancel");
            final Image copyButtonIcon = Resources.find("clipboard.png").getAsImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            final Image homeButtonIcon = Resources.find("house.png").getAsImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            final Image cancelButtonIcon = Resources.find("cancel.png").getAsImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            final JTextArea linkBox = new JTextArea(link);
            copyButton.setIcon(new ImageIcon(copyButtonIcon));
            homeButton.setIcon(new ImageIcon(homeButtonIcon));
            cancelButton.setIcon(new ImageIcon(cancelButtonIcon));
            linkBox.setEditable(false);

            homeButton.addActionListener(event -> paparazzi.setWindow(new MainPanel()));
            cancelButton.addActionListener(event -> paparazzi.setWindow(new MainPanel(this.image)));
            copyButton.addActionListener(event -> Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new TransferableString(link), new GenericClipboardOwner()));

            add(homeButton);
            add(copyButton);
            add(cancelButton);
            add(linkBox);
            paparazzi.getWindow().setSize(350, 100);
            paparazzi.getWindow().setVisible(true);
        });
    }

}