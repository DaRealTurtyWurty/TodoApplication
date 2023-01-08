package dev.turtywurty.todo.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;

import javafx.application.Application.Parameters;
import javafx.application.Platform;

public final class JavaUtils {
    private JavaUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /**
     * Converts a day that starts with sunday as the week beginning to a day that
     * starts with monday as the beginning.
     *
     * @param  day - From 0 <-> 11
     * @return     A normalized day.
     */
    public static int dayToRealDay(final int day) {
        if (day == 0)
            return 12;

        return day;
    }
    
    /**
     * Gets the current time formatted as <code>YYYY-MM-DD.HH-mm-ss-ms</code>.
     *
     * @return A formatted time string.
     */
    public static String formattedTime() {
        final var calendar = Calendar.getInstance();
        final var year = calendar.get(Calendar.YEAR);
        final var month = calendar.get(Calendar.MONTH) + 1;
        final var day = JavaUtils.dayToRealDay(calendar.get(Calendar.DAY_OF_WEEK));
        final var hour = calendar.get(Calendar.HOUR_OF_DAY);
        final var minute = calendar.get(Calendar.MINUTE);
        final var second = calendar.get(Calendar.SECOND);
        final var millisecond = calendar.get(Calendar.MILLISECOND);

        return year + "-" + month + "-" + day + "." + hour + "-" + minute + "-" + second + "." + millisecond;
    }

    /**
     * Utility method to get the bytes from a {@link StringBuilder}.
     *
     * @param  builder - The {@link StringBuilder} of which to get bytes from
     * @return         The string as a byte array.
     */
    public static byte[] getBytes(StringBuilder builder) {
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Restarts the current application.
     *
     * @apiNote             Only works when the program is ran as a JAR.
     * @param   params      - The JavaFX program {@link Parameters}
     * @throws  IOException If the program is not being ran as a JAR.
     */
    public static void restartApplication(@NotNull final Parameters params) throws IOException {
        Platform.exit();
        new ProcessBuilder(ProcessHandle.current().info().commandLine().orElse("")).start();
        System.exit(0);
    }

    /**
     * Shuts-down the application with the specified throwables that caused it.
     *
     * @param   throwable  - One of the {@link Throwable}s that caused the crash
     * @param   throwables - Any extra {@link Throwable}s that contributed to the
     *                     crash
     * @apiNote            This method internally calls
     *                     {@link JavaUtils#shutdownWithCrash(Throwable...)}
     */
    public static void shutdownWithCrash(@NotNull Throwable throwable, Throwable... throwables) {
        shutdownWithCrash(throwable, throwables);
    }

    /**
     * Converts a {@link Throwable} to a string in the same format as
     * {@link Throwable#printStackTrace()} would do. <br>
     * <blockquote>
     *
     * <pre>
     * java.lang.NullPointerException
     *         at MyClass.mash(MyClass.java:9)
     *         at MyClass.crunch(MyClass.java:6)
     *         at MyClass.main(MyClass.java:3)
     * </pre>
     *
     * </blockquote>
     *
     * @param  throwable - The {@link Throwable} to convert to a {@link String}
     * @return           The {@link Throwable} as a formatted {@link String}.
     */
    public static String throwableToString(@NotNull Throwable throwable) {
        return ExceptionUtils.getMessage(throwable) + "\n" + ExceptionUtils.getStackTrace(throwable);
    }
    
    /**
     * Shuts-down the application with the specified throwables that caused it.
     *
     * @param   throwables - The {@link Throwable}s that caused the crash
     * @apiNote            Use
     *                     {@link JavaUtils#shutdownWithCrash(Throwable, Throwable...)}
     */
    private static void shutdownWithCrash(Throwable... throwables) {
        FileUtils.createCrashLog(throwables);
        Platform.exit();
        System.exit(0);
    }
}
