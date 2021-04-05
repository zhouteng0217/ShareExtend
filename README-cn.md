Language: [English](https://github.com/zhouteng0217/ShareExtend/blob/master/README-en.md) | [中文简体](https://github.com/zhouteng0217/ShareExtend/blob/master/README.md)

# ShareExtend

[![pub package](https://img.shields.io/pub/v/share_extend.svg)](https://pub.dartlang.org/packages/share_extend)

调用系统分享的Flutter组件，支持分享文本、图片、视频和文件

## 安装

```
dependencies:
  share_extend: "^2.0.0"
```

### iOS

添加下面的key到工程的info.plist文件，路径 ```<project root>/ios/Runner/Info.plist```，用于将分享的图片保存到相册

```
<key>NSPhotoLibraryAddUsageDescription</key>
<string>这里填写为什么需要相册写入权限的描述语句</string>
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
ShareExtend.share("share text", "text","android share panel title","share subject");

//分享图片 （例子中使用了一个image_picker的插件来实现图片的选择)
File f =
    await ImagePicker.pickImage(source: ImageSource.gallery);
ShareExtend.share(f.path, "image");

//分享视频
File f = await ImagePicker.pickVideo(
        source: ImageSource.gallery);
ShareExtend.share(f.path, "video");

//分享文件
Directory dir = Platform.isAndroid
    ? await getExternalStorageDirectory()
    : await getApplicationDocumentsDirectory();
File testFile = new File("${dir.path}/flutter/test.txt");
if (!await testFile.exists()) {
  await testFile.create(recursive: true);
  testFile.writeAsStringSync("test for share documents file");
}
ShareExtend.share(testFile.path, "file");

//分享多图(借助了MultiImagePicker来多选获取图片图片，由于该库没有提供文件路径，因此demo里面先将图片保存为图片再调用分享)
_shareMultipleImages() async {
  List<Asset> assetList = await MultiImagePicker.pickImages(maxImages: 5);
  var imageList = List<String>();
  for (var asset in assetList) {
    String path =
        await _writeByteToImageFile(await asset.getByteData(quality: 30));
    imageList.add(path);
  }
  ShareExtend.shareMultiple(imageList, "image",subject: "share muti image");
}

Future<String> _writeByteToImageFile(ByteData byteData) async {
  Directory dir = Platform.isAndroid
      ? await getExternalStorageDirectory()
      : await getApplicationDocumentsDirectory();
  File imageFile = new File(
      "${dir.path}/flutter/${DateTime.now().millisecondsSinceEpoch}.png");
  imageFile.createSync(recursive: true);
  imageFile.writeAsBytesSync(byteData.buffer.asUint8List(0));
  return imageFile.path;
}

```