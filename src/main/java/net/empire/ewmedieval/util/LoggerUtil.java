package net.empire.ewmedieval.util;

import net.empire.ewmedieval.EwMedieval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for logging messages from the ewmedieval mod.
 */
public final class LoggerUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EwMedieval.MOD_ID);

    // Private constructor to prevent instantiation
    private LoggerUtil() {}

    /**
     * Logs a debug message if debug mode is enabled.
     */
    public static void logDebug(String msg) {
        if (EwMedieval.IS_DEBUG) {
            LOGGER.debug(msg);
        }
    }

    /**
     * Logs an info message.
     */
    public static void logInfo(String msg) {
        LOGGER.info(msg);
    }

    /**
     * Logs a warning message.
     */
    public static void logWarn(String msg) {
        LOGGER.warn(msg);
    }

    /**
     * Logs a trace message.
     */
    public static void logTrace(String msg) {
        LOGGER.trace(msg);
    }

    /**
     * Logs an error message.
     */
    public static void logError(String msg) {
        LOGGER.error(msg);
    }

    /**
     * Logs an error message with an exception.
     */
    public static void logError(String msg, Exception e) {
        LOGGER.error(msg, e);
    }
}
