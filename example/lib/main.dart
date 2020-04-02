import 'dart:async';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:multi_image_picker/multi_image_picker.dart';

import 'package:share_extend/share_extend.dart';
import 'package:image_picker/image_picker.dart';
import 'package:path_provider/path_provider.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Container(
          child: Center(
            child: Column(
              children: <Widget>[
                RaisedButton(
                  onPressed: () {
                    ShareExtend.share("share text", "text",
                        sharePanelTitle: "share text title",
                        subject: "share text subject");
                  },
                  child: Text("share text"),
                ),
                RaisedButton(
                  onPressed: () async {
                    File f = await ImagePicker.pickImage(
                        source: ImageSource.gallery);
                    if (f != null) {
                      ShareExtend.share(f.path, "image",
                          sharePanelTitle: "share image title",
                          subject: "share image subject");
                    }
                  },
                  child: Text("share image"),
                ),
                RaisedButton(
                  onPressed: () async {
                    File f = await ImagePicker.pickVideo(
                        source: ImageSource.gallery);
                    if (f != null) {
                      ShareExtend.share(f.path, "video");
                    }
                  },
                  child: Text("share video"),
                ),
                RaisedButton(
                  onPressed: () {
                    _shareStorageFile();
                  },
                  child: Text("share file"),
                ),
                RaisedButton(
                  onPressed: () {
                    _shareMultipleImages();
                  },
                  child: Text("share multiple images"),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  ///share multiple images
  _shareMultipleImages() async {
    List<Asset> assetList = await MultiImagePicker.pickImages(maxImages: 5);
    var imageList = List<String>();
    for (var asset in assetList) {
      String path =
          await _writeByteToImageFile(await asset.getByteData(quality: 30));
      imageList.add(path);
    }
    ShareExtend.shareMultiple(imageList, "image", subject: "share muti image");
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

  ///share the storage file
  _shareStorageFile() async {
    Directory dir = Platform.isAndroid
        ? await getExternalStorageDirectory()
        : await getApplicationDocumentsDirectory();
    File testFile = File("${dir.path}/flutter/test.txt");
    if (!await testFile.exists()) {
      await testFile.create(recursive: true);
      testFile.writeAsStringSync("test for share documents file");
    }
    ShareExtend.share(testFile.path, "file");
  }
}
