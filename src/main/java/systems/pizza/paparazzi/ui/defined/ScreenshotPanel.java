package systems.pizza.paparazzi.ui.defined;

import systems.pizza.paparazzi.Paparazzi;
import systems.pizza.paparazzi.ui.CustomPanel;
import systems.pizza.paparazzi.util.MultiscreenUtil;
import systems.pizza.paparazzi.util.Robots;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ScreenshotPanel extends CustomPanel {

    private final Rectangle2D dimensions = MultiscreenUtil.getTotalScreenDimensions();
    private Rectangle selectionZone = null;
    private BufferedImage image = null;

    @Override
    public void configure(Paparazzi paparazzi) {
        final int width = (int) Math.round(dimensions.getWidth());
        final int height = (int) Math.round(dimensions.getHeight());

        paparazzi.getWindow().dispose();
        paparazzi.getWindow().setUndecorated(true);
        paparazzi.getWindow().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        paparazzi.getWindow().setAlwaysOnTop(true);
        paparazzi.getWindow().setLocation(0, 0);
        paparazzi.getWindow().setSize(width, height);

        paparazzi.getWindow().addWindowFocusListener(new WindowFocusListener() {

            private boolean focused;

            @Override
            public void windowGainedFocus(WindowEvent event) {
                if (focused) {
                    paparazzi.setWindow(new ScreenshotPanel());
                    return;
                }

                focused = true;
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                if (focused)
                    paparazzi.setWindow(new MainPanel());
            }
        });
        paparazzi.getWindow().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
                    paparazzi.setWindow(new MainPanel());
            }
        });
        paparazzi.getWindow().addMouseMotionListener(new MouseMotionListener() {

            private Point start = new Point();

            @Override
            public void mouseMoved(MouseEvent event) {
                start = new Point(event.getX(), event.getY());
            }

            @Override
            public void mouseDragged(MouseEvent event) {
                final Point end = new Point(event.getX(), event.getY());
                int width = end.x - start.x;
                int height = end.y - start.y;
                boolean invertX = false;
                boolean invertY = false;

                if (selectionZone != null) {
                    if (height < 1 && height < selectionZone.height) {
                        height = Math.abs(height);
                        invertY = true;
                    }

                    if (width < 1 && width < selectionZone.width) {
                        width = Math.abs(width);
                        invertX = true;
                    }
                }

                selectionZone = new Rectangle(new Point(invertX ? end.x : start.x, invertY ? end.y : start.y), new Dimension(width, height));
                repaint();
            }
        });
        paparazzi.getWindow().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                if (selectionZone == null || selectionZone.width < 20 || selectionZone.height < 20) {
                    selectionZone = null;
                    repaint();
                    return;
                }

                paparazzi.setWindow(new MainPanel(image.getSubimage(selectionZone.x, selectionZone.y, selectionZone.width, selectionZone.height)));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics src) {
        super.paintComponent(src);

        if (this.image == null) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException ignored) {}

            final Robot robot = Robots.create();
            if (robot == null) throw new IllegalStateException("Failed to create robot instance on this platform!");
            this.image = robot.createScreenCapture(dimensions.getBounds());
        }

        final Graphics2D graphics = (Graphics2D) src;
        graphics.drawImage(this.image, 0, 0, this);

        if (selectionZone != null) {
            graphics.setColor(new Color(100, 100, 100));
            graphics.draw(selectionZone);
            graphics.setColor(new Color(255, 255, 255, 100));
            graphics.fill(selectionZone);
        }

        graphics.dispose();
    }

}
