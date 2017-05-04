# ALog

[![Travis](https://travis-ci.org/no-creativity/ALog.svg?branch=master)](https://travis-ci.org/no-creativity/ALog)
[![Releases](https://img.shields.io/github/release/no-creativity/ALog.svg)](https://github.com/no-creativity/ALog/releases/latest)
[![Bintray](https://api.bintray.com/packages/no-creativity/maven/ALog/images/download.svg)](https://bintray.com/no-creativity/maven/ALog/_latestVersion) 
[![JitPack](https://jitpack.io/v/no-creativity/ALog.svg)](https://jitpack.io/#no-creativity/ALog)
[![CodeCov](https://codecov.io/gh/no-creativity/ALog/branch/master/graph/badge.svg)](https://codecov.io/gh/no-creativity/ALog)
[![VersionEye](https://www.versioneye.com/user/projects/5827e1372f4754004399638c/badge.svg)](https://www.versioneye.com/user/projects/5827e1372f4754004399638c)

A Log wrapper for Android applications, which simplifies and redefines the way of printing a log.

## About Android Log

> The order in terms of verbosity, from least to most is ERROR, WARN, INFO, DEBUG, VERBOSE. Verbose should never be compiled into an application except during development. Debug logs are compiled in but stripped at runtime. Error, warning and info logs are always kept.

*Don't print anything log if possible, especially after released!*

If you still want to print logs, be careful.

## Add Dependencies

From JCenter of Bintray:

```groovy
dependencies {
    compile 'org.no_creativity.aar:ALog:0.4.0'
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
    compile 'com.github.no-creativity:ALog:0.4.0'
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

    // Print complicated information when debug.
    A.log.i(stringBuilder);
    A.log.i(set);
    A.log.i(map);

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

You can find more detail in [JavaDoc](https://jitpack.io/com/github/no-creativity/ALog/0.4.0/javadoc/).

## Version Information

[![GitHub tag](https://img.shields.io/github/tag/no-creativity/ALog.svg)](https://github.com/no-creativity/ALog/tags)
![commits-since](https://img.shields.io/github/commits-since/no-creativity/ALog/0.4.0.svg)

| Name             | Version |
| ----             | ------- |
| targetSdkVersion | 24      |
| minSdkVersion    | 8       |

## License

[![License](https://img.shields.io/github/license/no-creativity/ALog.svg)](LICENSE)

```license
MIT License

Copyright (c) 2017 Yan QiDong
```
