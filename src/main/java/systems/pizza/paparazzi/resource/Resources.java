package systems.pizza.paparazzi.resource;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Resources {

    public static Resource find(String path) {
        return find(Paths.get(path));
    }

    public static Resource find(Path path) {
        return find(path, true);
    }

    public static Resource find(String path, boolean internal) {
        return find(Paths.get(path), internal);
    }

    public static Resource find(Path path, boolean internal) {
        return new Resource(path, internal);
    }

}
