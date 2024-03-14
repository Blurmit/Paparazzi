package systems.pizza.paparazzi.resource;

import systems.pizza.paparazzi.Bootstrap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Resource {

    private final Path path;
    private final boolean internal;

    public Resource(final String path) {
        this(path, true);
    }

    public Resource(final Path path) {
        this(path, true);
    }

    public Resource(final String path, final boolean internal) {
        this(Paths.get(path), internal);
    }

    public Resource(final Path path, final boolean internal) {
        this.path = path;
        this.internal = internal;
    }

    public Path getPath() {
        return this.path;
    }

    public boolean isInternal() {
        return this.internal;
    }

    public InputStream getAsInputStream() {
        return Bootstrap.class.getResourceAsStream("/" + this.path.toString());
    }

    public BufferedImage getAsImage() {
        try {
            return ImageIO.read(this.internal ? Bootstrap.class.getResource("/" + this.path.toString()) : this.path.toUri().toURL());
        } catch (IOException e) {
            return null;
        }
    }

    public ImageIcon getAsIcon() {
        return new ImageIcon(getAsImage());
    }

}
