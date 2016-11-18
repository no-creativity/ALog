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
@SuppressWarnings("WeakerAccess")
public abstract class ALog {
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

    private static String getThreadInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element;
        element = stackTrace[TRACE_POSITION];
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
     */
    public void d(String message) {
        if (DEBUG) {
            Log.d(TAG, getThreadInfo() + message);
        }
    }

    /**
     * To print a warning message.
     * <p>
     * Note: It's always logged.
     *
     * @param message The information to be logged.
     */
    public void w(String message) {
        Log.w(TAG, getThreadInfo() + message);
    }

    /**
     * To print the message of {@link Throwable}.
     * <p>
     * Note: It's always logged.
     *
     * @param th The {@link Throwable} to be logged.
     */
    public void e(Throwable th) {
        Log.e(TAG, getThreadInfo() + th.getMessage());
    }

    /**
     * To print <b>What a Terrible Failure</b> caused by {@link Throwable}.
     * <p>
     * Note: It would cause a crash when debug, or would be logged.
     *
     * @param th The {@link Throwable} to be logged.
     * @throws RuntimeException Thrown when debug.
     */
    public void wtf(Throwable th) throws RuntimeException {
        if (DEBUG) {
            if (th instanceof RuntimeException) {
                throw (RuntimeException) th;
            } else {
                throw new RuntimeException(th);
            }
        } else {
            String traceString = Log.getStackTraceString(th);
            Log.e(TAG, traceString);
        }
    }
}
