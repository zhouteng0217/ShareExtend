import 'package:flutter/material.dart';
import 'dart:io';

import 'package:share_extend/share_extend.dart';
import 'package:image_picker/image_picker.dart';
import 'package:permission/permission.dart';
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
              onPressed: () async {
                File f =
                    await ImagePicker.pickImage(source: ImageSource.gallery);
                ShareExtend.share(f.path, "file");
              },
              child: new Text("share file"),
            ),
            new RaisedButton(
              onPressed: () {
                if (Platform.isAndroid) {
                  _shareFileWithPerm();
                }
              },
              child: new Text("share android external storage file"),
            ),
          ],
        )),
      ),
    );
  }

  /// share the external storage file ,first check the permission
  _shareFileWithPerm() async {
    if (await _checkPermission()) {
      _shareFile();
    } else {
      var result = await _requestPermission();

      /// to ask for permission
      if (result == PermissionStatus.allow) {
        _shareFile();
      }
    }
  }

  /// create a test file for the share example
  _shareFile() async {
    String filePath = await _createTestFileToShare();
    ShareExtend.share(filePath, "file");
  }

  ///create a test file to share
  _createTestFileToShare() async {
    Directory storageDir = await getExternalStorageDirectory();
    File testFile = new File("${storageDir.path}/flutter/test.txt");
    if (!await testFile.exists()) {
      await testFile.create(recursive: true);
    }
    testFile.writeAsStringSync("test for share");
    return testFile.path;
  }

  _checkPermission() async {
    List<Permissions> perms =
        await Permission.getPermissionStatus([PermissionName.Storage]);
    return perms[0].permissionStatus == PermissionStatus.allow;
  }

  _requestPermission() async {
    return await Permission.requestSinglePermission(PermissionName.Storage);
  }
}
