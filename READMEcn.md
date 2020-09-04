# HoverProcessView
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat)](http://opensource.org/licenses/MIT)
[![](https://jitpack.io/v/hzw1199/HoverProcessView.svg)](https://jitpack.io/#hzw1199/HoverProcessView)

![](/media/showcase.gif)

YouTube:  

[![IMAGE ALT TEXT](http://img.youtube.com/vi/oEDLgtpN0gk/0.jpg)](https://youtu.be/oEDLgtpN0gk "HoverProcessView")

## Features

* 镂空半透明圆形进度
* 支持开启和关闭动画
* 背景支持圆角矩形

## Usage

### Step 1

在project的build.gradle中加入以下语句:  

```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

在module的build.gradle中加入以下语句:  

```
dependencies {
    compile 'com.github.hzw1199:HoverProcessView:1.0.0'
}
```

### Step 2

创建配置并显示

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

支持的 xml 属性

| Attribute      | format        | describe  | default |
| :---------: | :-------------: |:-------------:|:-------------:|
| hoverColor |color|背景和进度圆形的颜色|#000000|
| radius |dimension|进度圆形半径|50 px|
| ringWidth |dimension|背景和进度圆形之间的透明圆环宽度|5 px|
| max |integer|进度最大值|100|
| progress |integer|初始进度|0|
| ringPadding |dimension|透明圆环padding|0|
| duration |integer|进度圆形转满一圈所需的时间毫秒数|400|
| roundCornerRadius |dimension|背景圆角半径|0|

* java

```java
HoverProcessView process = findViewById(R.id.process);
```

动画模式

```java
process.setProgress(50, true);
```

无动画模式

```java
process.setProgress(50, false);
```

无限模式

```java
process.startInfiniteProgress();
process.stopInfiniteProgress();
```

onDestroy:

```
process.stop();
```

## Proguard
无需配置混淆规则

## Tip

* 使用前请查看demo
* 若对你有帮助请加星

## About Me

* [Home Page](https://zongheng.pro)
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