import 'package:flutter/material.dart';
import 'dart:io';

import 'package:share_extend/share_extend.dart';
import 'package:image_picker/image_picker.dart';
import 'package:path_provider/path_provider.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: new Scaffold(
        appBar: new AppBar(
          title: const Text('Plugin example app'),
        ),
        body: new Center(
            child: new Column(
          children: <Widget>[
            new RaisedButton(
              onPressed: () {
                ShareExtend.share("share text", "text");
              },
              child: new Text("share text"),
            ),
            new RaisedButton(
              onPressed: () async {
                File f =
                    await ImagePicker.pickImage(source: ImageSource.gallery);
                ShareExtend.share(f.path, "image");
              },
              child: new Text("share image"),
            ),
            new RaisedButton(
              onPressed: () {
                _shareApplicationDocumentsFile();
              },
              child: new Text("share file"),
            ),
          ],
        )),
      ),
    );
  }

  ///share the documents file
  _shareApplicationDocumentsFile() async {
    Directory dir = await getApplicationDocumentsDirectory();
    File testFile = new File("${dir.path}/flutter/test.txt");
    if (!await testFile.exists()) {
      await testFile.create(recursive: true);
      testFile.writeAsStringSync("test for share documents file");
    }
    ShareExtend.share(testFile.path, "file");
  }
}
