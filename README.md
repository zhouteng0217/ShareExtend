Language: [English](https://github.com/zhouteng0217/ShareExtend/blob/master/README-en.md) | [中文简体](https://github.com/zhouteng0217/ShareExtend/blob/master/README.md)

# ShareExtend

[![pub package](https://img.shields.io/pub/v/share_extend.svg)](https://pub.dartlang.org/packages/share_extend)

调用系统分享的Flutter组件，支持分享文本、图片、视频和文件

## 安装

```
dependencies:
  share_extend: "^1.1.0"
```

### iOS

添加下面的key到工程的info.plist文件，路径 ```<project root>/ios/Runner/Info.plist```，用于将分享的图片保存到相册

```
<key>NSPhotoLibraryAddUsageDescription</key>
<string>这里填写为什么需要相写入册权限的描述语句</string>
```

### Android

如果涉及到要分享存储空间里面的文件，需要用到读写存储空间权限的，请在项目的android模块的下，添加读写权限，路径为 `<project root>/android/app/src/main/AndroidManifest.xml`

```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

## 导入

```
import 'package:share_extend/share_extend.dart';
```

## 使用

```

//分享文本
ShareExtend.share("share text", "text");

//分享图片 （例子中使用了一个image_picker的插件来实现图片的选择)
File f =
    await ImagePicker.pickImage(source: ImageSource.gallery);
ShareExtend.share(f.path, "image");

//分享视频
File f = await ImagePicker.pickVideo(
        source: ImageSource.gallery);
ShareExtend.share(f.path, "video");

//分享文件
Directory dir = await getApplicationDocumentsDirectory();
File testFile = new File("${dir.path}/flutter/test.txt");
if (!await testFile.exists()) {
  await testFile.create(recursive: true);
  testFile.writeAsStringSync("test for share documents file");
}
ShareExtend.share(testFile.path, "file");

///分享多图
_shareMultipleImages() async {
  List<Asset> assetList = await MultiImagePicker.pickImages(maxImages: 5);
  var imageList = List<String>();
  for (var asset in assetList) {
    imageList.add(await asset.filePath);
  }
  ShareExtend.shareMultiple(imageList, "image");
}

```