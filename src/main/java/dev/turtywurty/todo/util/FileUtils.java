package dev.turtywurty.todo.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import nl.altindag.console.ConsoleCaptor;

public final class FileUtils {
    private static final ConsoleCaptor CONSOLE_CAPTOR = new ConsoleCaptor();
    private static final Path LOGS = Path.of("logs");
    private static final Path LATEST_LOG = Path.of("logs/latest.log");
    private static final Path DEBUG_LOG = Path.of("logs/debug.log");
    private static final String CRASH_LOG = "logs/crash-log-%s.log";

    private FileUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /**
     * Utility method to easily append to a file.
     *
     * @param  file        - The file to append to
     * @param  bytes       - The bytes to append to the file
     * @throws IOException If an I/O Error occurs whilst writing to the file
     */
    public static void append(Path file, byte[] bytes) throws IOException {
        Files.write(file, bytes, StandardOpenOption.APPEND);
    }
    
    /**
     * Creates a crash log file at the running directory in the logs folder.
     *
     * @param throwables - The {@link Exception}s and {@link Error}s that should be
     *                   added to the crash log.
     */
    public static void createCrashLog(final Throwable... throwables) {
        try {
            final var location = Path.of(String.format(CRASH_LOG, JavaUtils.formattedTime()));
            final var builder = new StringBuilder();
            for (final Throwable throwable : throwables) {
                builder.append(JavaUtils.throwableToString(throwable) + "\n\n");
            }
            
            final byte[] bytes = JavaUtils.getBytes(builder);

            if (Files.notExists(location)) {
                Files.createFile(location);
                append(location, bytes);
            } else {
                final var newPath = Path.of(location.toString().replace(".log", "(1).log"));
                if (Files.exists(newPath)) {
                    Files.delete(newPath);
                    
                    write(location, bytes);
                    append(location,
                        ("\n\nDuplicate logs found and cleared:\n" + newPath + "\n" + location).getBytes());
                } else {
                    Files.createFile(newPath);
                    append(newPath, bytes);
                }
            }
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Creates the logs directory as well as creating the latest.log and debug.log.
     */
    public static void createLogs() {
        try {
            if (Files.notExists(LOGS)) {
                Files.createDirectory(LOGS);
            }
            
            if (Files.exists(LATEST_LOG)) {
                write(LATEST_LOG, new byte[0]);
            } else {
                Files.createFile(LATEST_LOG);
            }
            
            if (Files.exists(DEBUG_LOG)) {
                write(DEBUG_LOG, new byte[0]);
            } else {
                Files.createFile(DEBUG_LOG);
            }
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Utility method to easily write to a file.
     *
     * @param  file        - The file to write to
     * @param  bytes       - The bytes to write to the file
     * @throws IOException If an I/O Error occurs whilst writing to the file
     */
    public static void write(Path file, byte[] bytes) throws IOException {
        Files.write(file, bytes, StandardOpenOption.WRITE);
    }
}
