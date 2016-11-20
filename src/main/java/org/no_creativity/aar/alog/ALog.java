/*
 * MIT License
 *
 * Copyright (c) 2016 Yan QiDong
 */

package org.no_creativity.aar.alog;

import android.util.Log;

import java.util.Map;
import java.util.Set;

import static java.util.Map.Entry;

/**
 * It is a wrapper of {@link Log}, which simplifies and redefines the way of printing a log.
 *
 * @author yanqd0
 */
@SuppressWarnings("WeakerAccess")
public abstract class ALog {
    /**
     * The {@link Log} can not display the tail after 4096 characters.
     */
    protected static final int LOG_LENGTH_LIMIT = 0x1000;

    private static final int TRACE_POSITION = 4;

    private final String TAG;
    private final boolean DEBUG;

    /**
     * The constructor is only used by extending.
     *
     * @param tag The global tag.
     * @param debug The environment is debuggable or not.
     */
    protected ALog(String tag, boolean debug) {
        TAG = tag;
        DEBUG = debug;
    }

    /**
     * To show the file and method name only.
     * <p>
     * It's logged only when debug.
     */
    public void v() {
        if (DEBUG) {
            Log.v(TAG, getThreadInfo());
        }
    }

    /**
     * To print a debug message.
     * <p>
     * It's logged only when debug.
     *
     * @param message The information to be logged.
     * @throws IllegalArgumentException Thrown if the information is more than 4 KB when debug.
     */
    public void d(String message) throws IllegalArgumentException {
        if (DEBUG) {
            String information = getThreadInfo() + message;
            Log.d(TAG, information);

            checkLength(information);
        }
    }

    /**
     * To print complicated information.
     * <p>
     * It's logged only when debug.
     *
     * @param builder The information holder.
     * @throws IllegalArgumentException Thrown if the information is more than 4 KB when debug.
     */
    public void i(StringBuilder builder) {
        if (DEBUG) {
            builder.insert(0, getThreadInfo());
            String information = builder.toString();
            Log.i(TAG, information);

            checkLength(information);
        }
    }

    /**
     * To print complicated information.
     * <p>
     * It's logged only when debug.
     *
     * @param set The information holder.
     * @throws IllegalArgumentException Thrown if the information is more than 4 KB when debug.
     */
    public void i(Set set) throws IllegalArgumentException {
        if (DEBUG) {
            String information = generateInformation(getThreadInfo(), set);
            Log.i(TAG, information);

            checkLength(information);
        }
    }

    /**
     * To print complicated information.
     * <p>
     * It's logged only when debug.
     *
     * @param map The information holder.
     * @throws IllegalArgumentException Thrown if the information is more than 4 KB when debug.
     */
    public void i(Map map) throws IllegalArgumentException {
        if (DEBUG) {
            String information = generateInformation(getThreadInfo(), map);
            Log.i(TAG, information);

            checkLength(information);
        }
    }

    private String generateInformation(String threadInfo, Set set) {
        StringBuilder builder = new StringBuilder(threadInfo);
        builder.append('[');
        int i = 0;
        for (Object o : set) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append(o);
            i++;
        }
        builder.append(']');
        return builder.toString();
    }

    private String generateInformation(String threadInfo, Map map) {
        StringBuilder builder = new StringBuilder(threadInfo);
        builder.append('{');
        int i = 0;
        @SuppressWarnings("unchecked")
        Set<Entry> set = map.entrySet();
        for (Entry entry : set) {
            if (i != 0) {
                builder.append(',');
            }
            builder.append(entry.getKey());
            builder.append(':');
            builder.append(entry.getValue());
            i++;
        }
        builder.append('}');
        return builder.toString();
    }

    /**
     * To print a warning message.
     * <p>
     * Note: It's always logged.
     *
     * @param message The information to be logged.
     * @throws IllegalArgumentException Thrown if the whole information is more than 4 KB.
     */
    public void w(String message) throws IllegalArgumentException {
        String information = getThreadInfo() + message;
        Log.w(TAG, information);

        if (DEBUG) {
            checkLength(information);
        }
    }

    /**
     * To print the message of {@link Throwable}.
     * <p>
     * Note: It's always logged.
     *
     * @param throwable The {@link Throwable} to be logged.
     * @throws IllegalArgumentException Thrown if the whole information is more than 4 KB.
     */
    public void e(Throwable throwable) throws IllegalArgumentException {
        String information = getThreadInfo() + throwable.getMessage();
        Log.e(TAG, information);

        if (DEBUG) {
            checkLength(information);
        }
    }

    /**
     * To print <b>What a Terrible Failure</b> caused by {@link Throwable}.
     * <p>
     * Note: It would cause a crash when debug, or would be logged.
     *
     * @param throwable The {@link Throwable} to be logged.
     * @throws RuntimeException Thrown when debug.
     */
    public void wtf(Throwable throwable) throws RuntimeException {
        if (DEBUG) {
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            } else {
                throw new RuntimeException(throwable);
            }
        } else {
            String traceString = Log.getStackTraceString(throwable);
            Log.e(TAG, traceString);
        }
    }

    /**
     * To print <b>What a Terrible Failure</b> with a message.
     * <p>
     * Note: It would cause a crash when debug, or would be logged.
     * @param message The information indicating the failure.
     * @throws RuntimeException Thrown when debug.
     */
    public void wtf(String message) throws RuntimeException {
        if (DEBUG) {
            throw new RuntimeException(message);
        } else {
            Log.e(TAG, getThreadInfo() + message);
        }
    }

    /**
     * To alert developers if the information is more than 4 KB.
     * <p>
     * Note: If the {@link String} is more than 4 KB, the tail will be trimmed.
     *
     * @param information The String to be checked.
     */
    private static void checkLength(String information) throws IllegalArgumentException {
        if (information.length() > LOG_LENGTH_LIMIT) {
            throw new IllegalArgumentException("The information is more than 4 KB!");
        }
    }

    private static String getThreadInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTrace[TRACE_POSITION];
        return getFileName(element) + "." + element.getMethodName() + "() ";
    }

    private static String getFileName(StackTraceElement element) {
        String file = element.getFileName();
        return file.substring(0, file.lastIndexOf("."));
    }
}
