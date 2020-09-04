# HoverProcessView
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat)](http://opensource.org/licenses/MIT)
[![](https://jitpack.io/v/hzw1199/HoverProcessView.svg)](https://jitpack.io/#hzw1199/HoverProcessView)

[中文看这里](/READMEcn.md)  

![](/media/showcase.gif)

YouTube:  

[![IMAGE ALT TEXT](http://img.youtube.com/vi/oEDLgtpN0gk/0.jpg)](https://youtu.be/oEDLgtpN0gk "HoverProcessView")

# Features

* Hover circle process
* With / without animation
* Background round corner

# Usage

### Step 1

Add it in your build.gradle at the end of repositories:  

```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Add the dependency in the form:  

```
dependencies {
    compile 'com.github.hzw1199:HoverProcessView:1.0.0'
}
```

### Step 2

Configure and display

* layout

```xml
<com.wuadam.hoverprocess.HoverProcessView
    android:id="@+id/process"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:duration="1000"
    app:hoverColor="#80000000"
    app:max="100"
    app:progress="30"
    app:radius="48dp"
    app:ringWidth="4dp"
    app:roundCornerRadius="8dp" />
```

Supported xml attributes

| Attribute      | format        | describe  | default |
| :---------: | :-------------: |:-------------:|:-------------:|
| hoverColor |color|background and process circle color|#000000|
| radius |dimension|process circle radius|50 px|
| ringWidth |dimension|width of transparent ring between background and process circle|5 px|
| max |integer|max value of process|100|
| progress |integer|initial value of process|0|
| ringPadding |dimension|padding for transparent ring|0|
| duration |integer|duration for a full process animation from 0 to max in millisecond|400|
| roundCornerRadius |dimension|background corner radius|0|

* java

```java
HoverProcessView process = findViewById(R.id.process);
```

Animate mode

```java
process.setProgress(50, true);
```

No animate mode

```java
process.setProgress(50, false);
```

Infinite mode

```java
process.startInfiniteProgress();
process.stopInfiniteProgress();
```

onDestroy:

```
process.stop();
```

## Proguard
No need to add more proguard rules.

## Tip

* Please check the demo before use.
* If this project helps you, please star me.

## About Me

* [Home Page](https://zongheng.pro/index.html)
* [Blog](https://blog.zongheng.pro)

## License

```
The MIT License (MIT)

Copyright (c) 2020 HoverProcessView

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
