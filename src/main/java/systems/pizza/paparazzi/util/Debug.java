package systems.pizza.paparazzi.util;

import java.util.Date;

public class Debug {

    public static void log(String message) {
        log(Level.INFO, message);
    }

    public static void log(Level level, String message) {
        System.out.println(level.color.toString() + "[" + new Date() + "] [" + level.name() + "] " + message);
    }

    public static void log(Level level, String message, Throwable t) {
        log(level, message + ". A " + t.getClass().getName() + " was thrown: " + t.getMessage());

        final StackTraceElement[] stackTrace = t.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            StackTraceElement e = stackTrace[i];
            log(level, "    " + (i == stackTrace.length - 1 ? "⮡" : "|") + " " + e.getClassName() + "." + e.getMethodName() + " was executed, " + (e.isNativeMethod() ? "which is a native method" : "in class " + e.getFileName() + " on line " + e.getLineNumber()) + ".");
        }
    }

    public enum Level {
        INFO(TerminalColor.RESET),
        WARN(TerminalColor.YELLOW),
        ERROR(TerminalColor.RED);

        private final TerminalColor color;

        Level(TerminalColor color) {
            this.color = color;
        }

        public TerminalColor getColor() {
            return this.color;
        }

    }

}
