Language: [English](https://github.com/zhouteng0217/ShareExtend/blob/master/README.md) | [中文简体](https://github.com/zhouteng0217/ShareExtend/blob/master/README-cn.md)

# ShareExtend

[![pub package](https://img.shields.io/pub/v/share_extend.svg)](https://pub.dartlang.org/packages/share_extend)

A Flutter plugin for iOS and Android for sharing text, image, video and file with system ui. 

## Installation

First, add `share_extend` as a dependency in your pubspec.yaml file.

```
dependencies:
  share_extend: "^2.0.0"
```

### iOS

Add the following key to your info.plist file, located in `<project root>/ios/Runner/Info.plist` for saving shared images to photo library.

```
<key>NSPhotoLibraryAddUsageDescription</key>
<string>describe why your app needs access to write photo library</string>
```

### Android

If your project needs read and write permissions for sharing external storage file, please add the following permissions to your AndroidManifest.xml, located in `<project root>/android/app/src/main/AndroidManifest.xml`

```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

## Import

```
import 'package:share_extend/share_extend.dart';
```


## Example

```

//share text
ShareExtend.share("share text", "text","android share panel title","share subject");

//share image
File f =
    await ImagePicker.pickImage(source: ImageSource.gallery);
ShareExtend.share(f.path, "image");

//share video
File f = await ImagePicker.pickVideo(
        source: ImageSource.gallery);
ShareExtend.share(f.path, "video");

//share file
Directory dir = Platform.isAndroid
    ? await getExternalStorageDirectory()
    : await getApplicationDocumentsDirectory();
File testFile = new File("${dir.path}/flutter/test.txt");
if (!await testFile.exists()) {
  await testFile.create(recursive: true);
  testFile.writeAsStringSync("test for share documents file");
}
ShareExtend.share(testFile.path, "file");

///share multiple images
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