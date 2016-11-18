# ALog

[![Travis](https://travis-ci.org/no-creativity/ALog.svg?branch=master)](https://travis-ci.org/no-creativity/ALog)
[![Releases](https://img.shields.io/github/release/no-creativity/ALog.svg)](https://github.com/no-creativity/ALog/releases/latest)
[![Bintray](https://api.bintray.com/packages/no-creativity/maven/ALog/images/download.svg)](https://bintray.com/no-creativity/maven/ALog/_latestVersion) 
[![JitPack](https://jitpack.io/v/no-creativity/ALog.svg)](https://jitpack.io/#no-creativity/ALog)
[![CodeCov](https://codecov.io/gh/no-creativity/ALog/branch/master/graph/badge.svg)](https://codecov.io/gh/no-creativity/ALog)
[![VersionEye](https://www.versioneye.com/user/projects/5827e1372f4754004399638c/badge.svg)](https://www.versioneye.com/user/projects/5827e1372f4754004399638c)

A Log wrapper for Android applications.

## About Android Log

> The order in terms of verbosity, from least to most is ERROR, WARN, INFO, DEBUG, VERBOSE. Verbose should never be compiled into an application except during development. Debug logs are compiled in but stripped at runtime. Error, warning and info logs are always kept.

*Don't print anything log if possible, especially after released!*

If you still want to print logs, be careful.

## Add Dependencies

From JCenter of Bintray:

```groovy
dependencies {
    compile 'org.no_creativity.aar:ALog:0.3.0'
}
```

From JitPack:

- Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        ..
        maven { url "https://jitpack.io" }
    }
}
```

- Add the dependency:

```groovy
dependencies {
    compile 'com.github.no-creativity:ALog:0.3.0'
}
```

## Usage

The `ALog` should be extended like this:

```java
public class A extends ALog {
    public static final String TAG = "YourGlobalTag";
    public static final ALog log = new A(TAG);

    private A(String tag) {
        super(tag, BuildConfig.DEBUG);
    }
}
```

And the class `A` should be used like this:

```java
    // Print only when debug.
    A.log.v(); // Print the file and method name.
    A.log.d("Message"); // Print the message with the file and method name.
    A.log.i(stringBuilder); // Print complicated information.

    // Print always.
    A.log.w("Message");
    A.log.e(exception); // Print the exception message.

    // Crash when debug, or print when release.
    A.log.wtf(exception); // Print stack traces.
    A.log.wtf("Message"); // Print the failure message.
```

Finally, the logs could be filtered like this:

```sh
adb logcat -s YourGlobalTag
```

## Version Information

[![GitHub tag](https://img.shields.io/github/tag/no-creativity/ALog.svg)](https://github.com/no-creativity/ALog/tags)

| Name             | Version |
| ----             | ------- |
| targetSdkVersion | 24      |
| minSdkVersion    | 8       |

## License

[![License](https://img.shields.io/github/license/no-creativity/ALog.svg)](LICENSE)

```license
MIT License

Copyright (c) 2016 Yan QiDong

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
