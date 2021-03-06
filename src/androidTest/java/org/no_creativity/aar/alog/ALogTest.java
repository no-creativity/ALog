/*
 * MIT License
 *
 * Copyright (c) 2016 Yan QiDong
 */

package org.no_creativity.aar.alog;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ALogTest {
    private static final String TAG = ALogTest.class.getSimpleName();
    private static final String TOO_LONG;

    static {
        StringBuilder builder = new StringBuilder("This information is");
        String repeat = " too long and";
        for (int i = 0; i < 400; i++) {
            builder.append(repeat);
        }
        TOO_LONG = builder.toString();
    }

    @SuppressWarnings("ThrowableInstanceNeverThrown")
    private final Throwable WTF_TH = new Throwable("WTF");
    private final String WTF_MSG = WTF_TH.getMessage();

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("org.no_creativity.aar.alog.test", appContext.getPackageName());
    }

    @Test
    public void testV() throws Exception {
        final String MSG = "ALogTest.testV()";

        LogObserver observerA = new LogObserver(MSG, LogLevel.V);
        A.log.v();
        assertFalse(observerA.getResult());

        LogObserver observerB = new LogObserver(MSG, LogLevel.V);
        B.log.v();
        assertTrue(observerB.getResult());
    }

    @Test
    public void testD() throws Exception {
        final String INFO = "debug";
        final String MSG = "ALogTest.testD() " + INFO;

        LogObserver observerA = new LogObserver(MSG, LogLevel.D);
        A.log.d(INFO);
        assertFalse(observerA.getResult());

        LogObserver observerB = new LogObserver(MSG, LogLevel.D);
        B.log.d(INFO);
        assertTrue(observerB.getResult());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkLengthOfD() throws Exception {
        B.log.d(TOO_LONG);
    }

    @Test
    public void testI0() throws Exception {
        final String INFO = "info";
        final String MSG = "ALogTest.testI0() " + INFO;
        StringBuilder builder = new StringBuilder(INFO);

        LogObserver observerA = new LogObserver(MSG, LogLevel.I);
        A.log.i(builder);
        assertFalse(observerA.getResult());

        LogObserver observerB = new LogObserver(MSG, LogLevel.I);
        B.log.i(builder);
        assertTrue(observerB.getResult());
    }

    @Test
    public void testI1() throws Exception {
        final String INFO = "[a, b, c]";
        final String MSG = "ALogTest.testI1() " + INFO;
        HashSet<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");

        LogObserver observerA = new LogObserver(MSG, LogLevel.I);
        A.log.i(set);
        assertFalse(observerA.getResult());

        LogObserver observerB = new LogObserver(MSG, LogLevel.I);
        B.log.i(set);
        assertTrue(observerB.getResult());
    }

    @Test
    public void testI2() throws Exception {
        final String INFO = "{a:1,b:2,c:3}";
        final String MSG = "ALogTest.testI2() " + INFO;
        HashMap<Object, Object> map = new HashMap<>();
        map.put('a', '1');
        map.put('b', '2');
        map.put('c', '3');

        LogObserver observerA = new LogObserver(MSG, LogLevel.I);
        A.log.i(map);
        assertFalse(observerA.getResult());

        LogObserver observerB = new LogObserver(MSG, LogLevel.I);
        B.log.i(map);
        assertTrue(observerB.getResult());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkLengthOfI0() throws Exception {
        B.log.i(new StringBuilder(TOO_LONG));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkLengthOfI1() throws Exception {
        HashSet<Object> set = new HashSet<>();
        for (int i = 0; i < ALog.LOG_LENGTH_LIMIT; i++) {
            set.add(i);
        }
        B.log.i(set);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkLengthOfI2() throws Exception {
        HashMap<Object, Object> map = new HashMap<>();
        for (int i = 0; i < ALog.LOG_LENGTH_LIMIT; i++) {
            map.put(i, i);
        }
        B.log.i(map);
    }

    @Test
    public void testW() throws Exception {
        final String INFO = "warning";
        final String MSG = "ALogTest.testW() " + INFO;

        LogObserver observerA = new LogObserver(MSG, LogLevel.W);
        A.log.w(INFO);
        assertTrue(observerA.getResult());

        LogObserver observerB = new LogObserver(MSG, LogLevel.W);
        B.log.w(INFO);
        assertTrue(observerB.getResult());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkLengthOfW() throws Exception {
        B.log.w(TOO_LONG);
    }

    @Test
    public void testE() throws Exception {
        final Throwable TH = new Throwable("error");
        final String MSG = "ALogTest.testE() " + TH.getMessage();

        LogObserver observerA = new LogObserver(MSG, LogLevel.E);
        A.log.e(TH);
        assertTrue(observerA.getResult());

        LogObserver observerB = new LogObserver(MSG, LogLevel.E);
        B.log.e(TH);
        assertTrue(observerB.getResult());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkLengthOfE() throws Exception {
        B.log.e(new Exception(TOO_LONG));
    }

    @Test
    public void testWtf0() throws Exception {
        LogObserver observer = new LogObserver(WTF_MSG, LogLevel.E);
        A.log.wtf(WTF_TH);
        assertTrue(observer.getResult());
    }

    @Test(expected = RuntimeException.class)
    public void testWtf1() throws Exception {
        B.log.wtf(new Exception(WTF_TH));
    }

    @Test(expected = IllegalStateException.class)
    public void testWtf2() throws Exception {
        B.log.wtf(new IllegalStateException(WTF_TH));
    }

    @Test
    public void testWtf3() throws Exception {
        final String INFO = "WTF";
        final String MSG = "ALogTest.testWtf3() " + INFO;
        LogObserver observer = new LogObserver(MSG, LogLevel.E);
        A.log.wtf(INFO);
        assertTrue(observer.getResult());
    }

    @Test(expected = RuntimeException.class)
    public void testWtf4() throws Exception {
        B.log.wtf("WTF");
    }

    enum LogLevel {
        V,
        D,
        I,
        W,
        E,
    }

    private static class A extends ALog {
        static final ALog log = new A(TAG);

        private A(String tag) {
            super(tag, false);
        }
    }

    private static class B extends ALog {
        static final ALog log = new B(TAG);

        private B(String tag) {
            super(tag, true);
        }
    }

    class LogObserver {
        private final String message;
        private final LogLevel level;
        private final Runtime runtime;
        private Boolean result = null;

        LogObserver(String message, LogLevel level) {
            this.message = message;
            this.level = level;
            this.runtime = Runtime.getRuntime();
            clearLog();
        }

        private void clearLog() {
            try {
                this.runtime.exec("logcat -c").waitFor();
            } catch (InterruptedException | IOException e) {
                fail(e.getMessage());
            }
        }

        boolean getResult() {
            if (this.result == null) {
                observe();
            }
            return this.result;
        }

        private void observe() {
            Process process = createObserverProcess();
            try {
                this.result = checkLog(process.getInputStream());
            } catch (IOException e) {
                fail(e.getMessage());
            } finally {
                process.destroy();
            }
        }

        private boolean checkLog(InputStream in) throws IOException {
            InputStreamReader streamReader = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(streamReader);
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.contains(this.message)) {
                    return true;
                }
            }
            return false;
        }

        private Process createObserverProcess() {
            try {
                return this.runtime.exec("logcat -ds " + TAG + ":" + this.level);
            } catch (IOException e) {
                fail(e.getMessage());
                throw new IllegalStateException(e);
            }
        }
    }
}
