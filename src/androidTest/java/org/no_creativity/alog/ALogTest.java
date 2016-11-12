package org.no_creativity.alog;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    @Before
    public void clearLog() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("logcat -c").waitFor();
        } catch (InterruptedException | IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("org.no_creativity.alog.test", appContext.getPackageName());
    }

    @Test
    public void testV() throws Exception {
        final String MSG = "ALogTest.testV()";

        LogObserver observerA = new LogObserver(MSG, LogLevel.V);
        A.log.v();
        assertTrue(observerA.getResult());

        clearLog();

        LogObserver observerB = new LogObserver(MSG, LogLevel.V);
        B.log.v();
        assertFalse(observerB.getResult());
    }

    enum LogLevel {
        V,
    }

    private static class A extends ALog {
        static final ALog log = new A(TAG);

        private A(String tag) {
            super(tag, true);
        }
    }

    private static class B extends ALog {
        static final ALog log = new B(TAG);

        private B(String tag) {
            super(tag, false);
        }
    }

    class LogObserver {
        private final String message;
        private final LogLevel level;
        private Boolean result = null;

        LogObserver(String message, LogLevel level) throws InterruptedException {
            this.message = message;
            this.level = level;
            B.log.v();
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        private void observe() {
            B.log.v();
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
            B.log.v();
            InputStreamReader streamReader = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(streamReader);
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.contains(this.message)) {
                    return true;
                }
            }
            return false;
        }

        @NonNull
        private Process createObserverProcess() {
            Runtime runtime = Runtime.getRuntime();
            try {
                return runtime.exec("logcat -ds " + TAG + ":" + this.level);
            } catch (IOException e) {
                fail(e.getMessage());
                throw new IllegalThreadStateException(e.getMessage());
            }
        }

        boolean getResult() throws InterruptedException {
            if (this.result == null) {
                observe();
            }
            return this.result;
        }
    }
}