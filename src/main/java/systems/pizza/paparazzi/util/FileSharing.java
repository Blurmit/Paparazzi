package systems.pizza.paparazzi.util;

import systems.pizza.paparazzi.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Base64;

public class FileSharing {

    public static String upload(final BufferedImage image) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) Constants.UPLOAD_URL.openConnection();
        connection.setRequestProperty("Host", "images.pizza.systems");
        connection.setRequestProperty("User-Agent", "File-Sharing");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        final ByteArrayOutputStream imageOutput = new ByteArrayOutputStream();
        ImageIO.write(image, "png", imageOutput);

        final String encodedInput = Base64.getEncoder().encodeToString(imageOutput.toByteArray());
        connection.getOutputStream().write(("{\"image\":\"" + encodedInput + "\"}").getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();
        String input;

        while ((input = reader.readLine()) != null) {
            content.append(input);
        }

        reader.close();
        connection.disconnect();

        return Constants.BASE_URL + content.toString();
    }

}
