# ShareExtend
调用系统分享的Flutter组件，支持分享文本，图片和文件

## 安装

```
dependencies:
  share_extend: "^1.0.3"
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

//分享文件
Directory dir = await getApplicationDocumentsDirectory();
File testFile = new File("${dir.path}/flutter/test.txt");
if (!await testFile.exists()) {
  await testFile.create(recursive: true);
  testFile.writeAsStringSync("test for share documents file");
}
ShareExtend.share(testFile.path, "file"); 

```

