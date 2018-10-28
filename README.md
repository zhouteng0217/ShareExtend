# ShareExtend
调用系统分享的Flutter组件，支持分享文本，图片和文件

## 安装

```
dependencies:
  share_extend: "^1.0.0"
```
## 导入
```
import 'package:share_extend/share_extend.dart';
```

## 使用

* 如果要分享手机外部存储(external storage)中的图片和文件，对于Android工程来说，
   1) 需要配置WRITE_EXTERNAL_STORAGE权限，在android工程AndroidManifest.xml中配置
   ```
      <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
   ```
    对于targetSdkVersion>=23的版本，需要动态获取权限
    动态权限获取，可以借助其他第三方flutter插件来实现

   2) 需要配置FileProvider来实现文件的分享，在android工程AndroidManifest.xml中配置
   
  ```
          <provider
              android:name="android.support.v4.content.FileProvider"
              android:authorities="{your_authorities}"
              android:grantUriPermissions="true"
              android:exported="false">
              <meta-data
                  android:name="android.support.FILE_PROVIDER_PATHS"
                  android:resource="@xml/{your_provider_file}" />
          </provider>
  ```
  ```{your_authorities}```  你定义的authorities

  ```{your_provider_file}```  你的provider配置文件名

  工程的res文件夹下，新建一个xml文件夹，添加一个{your_provider_file}的xml文件，配置类似如下：

  ```
  <?xml version="1.0" encoding="utf-8"?>
  <resources>
      <paths>
          <external-path
              name="external_cache"
              path="" />
      </paths>
  </resources>
  ```
  ### Dart中调用

  ```
    // authorities 上面定义authorities
    
    //分享文本（只分享文本时，authorities参数是不必需的)
    ShareExtend.share("share text", "text", authorities);
    
    //分享图片 （例子中使用了一个image_picker的插件来实现图片的选择)
    File f =
        await ImagePicker.pickImage(source: ImageSource.gallery);
    ShareExtend.share(f.path, "image", authorities);
    //分享文件
    File f =
      await ImagePicker.pickImage(source: ImageSource.gallery);
    ShareExtend.share(f.path, "file", authorities);   
  ```

