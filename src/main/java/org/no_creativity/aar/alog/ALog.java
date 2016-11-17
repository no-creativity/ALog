/*
 * MIT License
 *
 * Copyright (c) 2016 Yan QiDong
 */

package org.no_creativity.aar.alog;

import android.util.Log;

/**
 * It is a wrapper of {@link Log}, which provides some useful functions.
 *
 * @author yanqd0
 */
public abstract class ALog {
    private static final int TRACE_POSITION = 4;
    private static final String ERROR_INFO = "UNKNOWN_FILE.UNKNOWN_METHOD() ";

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

    private static String getThreadInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element;
        try {
            element = stackTrace[TRACE_POSITION];
        } catch (IndexOutOfBoundsException e) {
            return ERROR_INFO;
        }
        return getFileName(element) + "." + element.getMethodName() + "() ";
    }

    private static String getFileName(StackTraceElement element) {
        String file = element.getFileName();
        return file.substring(0, file.lastIndexOf("."));
    }

    /**
     * To show the file and method name only.
     * <p>
     * It's logged only when debug.
     */
    @SuppressWarnings("WeakerAccess")
    public void v() {
        if (DEBUG) {
            Log.v(TAG, getThreadInfo());
        }
    }

    /**
     * @param message The information to be logged.
     * <p>
     * It's logged only when debug.
     */
    @SuppressWarnings("WeakerAccess")
    public void d(String message) {
        if (DEBUG) {
            Log.d(TAG, getThreadInfo() + message);
        }
    }

    /**
     * To print the message of {@link Throwable}.
     * <p>
     * It's logged only when debug.
     *
     * @param th The {@link Throwable} to be logged.
     */
    @SuppressWarnings("WeakerAccess")
    public void w(Throwable th) {
        if (DEBUG) {
            Log.w(TAG, getThreadInfo() + th.getMessage());
        }
    }
}
