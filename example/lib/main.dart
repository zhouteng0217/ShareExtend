import 'package:flutter/material.dart';
import 'dart:io';

import 'package:share_extend/share_extend.dart';
import 'package:image_picker/image_picker.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  ///define in android project AndroidManifest.xml for FileProvider
  static const String authorities = "com.zt.shareextend.fileprovider";

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
                ShareExtend.share("share text", "text", authorities);
              },
              child: new Text("share text"),
            ),
            new RaisedButton(
              onPressed: () async {
                File f =
                    await ImagePicker.pickImage(source: ImageSource.gallery);
                ShareExtend.share(f.path, "image", authorities);
              },
              child: new Text("share image"),
            ),
            new RaisedButton(
              onPressed: () async {
                File f =
                    await ImagePicker.pickImage(source: ImageSource.gallery);
                ShareExtend.share(f.path, "file", authorities);
              },
              child: new Text("share file"),
            ),
          ],
        )),
      ),
    );
  }
}
