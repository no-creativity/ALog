# ALog

[![Travis](https://travis-ci.org/no-creativity/ALog.svg?branch=master)](https://travis-ci.org/no-creativity/ALog)
[![Releases](https://img.shields.io/github/release/no-creativity/ALog.svg)](https://github.com/no-creativity/ALog/releases/latest)
[![CodeCov](https://codecov.io/gh/no-creativity/ALog/branch/master/graph/badge.svg)](https://codecov.io/gh/no-creativity/ALog)
[![VersionEye](https://www.versioneye.com/user/projects/5827e1372f4754004399638c/badge.svg)](https://www.versioneye.com/user/projects/5827e1372f4754004399638c)

A Log wrapper for Android applications.

## About Android Log

> The order in terms of verbosity, from least to most is ERROR, WARN, INFO, DEBUG, VERBOSE. Verbose should never be compiled into an application except during development. Debug logs are compiled in but stripped at runtime. Error, warning and info logs are always kept.

*Don't to print anything log if possible!*

If you still want to print logs, be careful.

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
    A.log.v();
    A.log.d("Hello");
```

Finally, the logs should be searched like this:

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
