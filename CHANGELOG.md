## 2.0.0
* support audio share
* null safety support

## 1.1.9
* fix ios not found module issue.

## 1.1.8
* support android embedding v2 api
* add extraText option for android Intent.EXTRA_TEXT when sharing image or file.

## 1.1.7
* set default popover point on the ipad

## 1.1.6
* fix some permission issues on some devices.
* update lower bound dart dependency to 2.0.0.

## 1.1.5
* fix provider issues for api < 24

## 1.1.4
* add grant uri permission on Android

## 1.1.3
* add option subject for sharing multiple images

## 1.1.2
* rebuild the provider code on android platform
* update sample code

## 1.1.1
* add option param "share panel title" on android
* add option param "subject" on both android and ios
* update sample code with latest image_picker plugin version that fixed video picker bugs

## 1.1.0
* 添加多图，多文件分享功能
* 合并image_picker在ios13中选取视频不可用的补丁

## 1.0.9
* 移除了Android端的内置存储的读写权限，改成由APP端来按需求配置

## 1.0.7
* Android端修改了FileProvider的authorities，继承FileProvider，防止FileProvider冲突

## 1.0.6
* 修复了android端分享应用沙盒内文件可能出错的bug
* 优化了android端分享时的权限请求逻辑

## 1.0.5
* Fix bugs when sharing videos to some apps like WeChat.

## 1.0.4

* Updated to Support Latest Android Dependencies


## 0.0.1

* TODO: Describe initial release.
