/// A flutter plugin to share text, image, file with system ui.
/// It is compatible with both android and ios.
///
///
/// A open source authorized by zhouteng [https://github.com/zhouteng0217/ShareExtend](https://github.com/zhouteng0217/ShareExtend).

import 'dart:async';
import 'package:flutter/services.dart';
import 'dart:ui';

/// Plugin for summoning a platform share sheet.
class ShareExtend {
  /// [MethodChannel] used to communicate with the platform side.
  static const MethodChannel _channel =
      const MethodChannel('com.zt.shareextend/share_extend');

  ///
  /// [sharePositionOrigin] only supports ios
  ///
  static Future<void> shareMultiple(List<String> list, String type,
      {Rect sharePositionOrigin, String sharePanelTitle, String subject}) {
    assert(list != null && list.isNotEmpty);
    return _shareInner(list, type,
        sharePositionOrigin: sharePositionOrigin,
        subject: subject,
        sharePanelTitle: sharePanelTitle);
  }

  static Future<void> share(String text, String type,
      {Rect sharePositionOrigin, String sharePanelTitle, String subject = ""}) {
    assert(text != null);
    assert(text.isNotEmpty);
    List<String> list = [text];
    return _shareInner(list, type,
        sharePositionOrigin: sharePositionOrigin,
        sharePanelTitle: sharePanelTitle,
        subject: subject);
  }

  /// method to share with system ui
  ///  It uses the ACTION_SEND Intent on Android and UIActivityViewController
  /// on iOS.
  /// [list] can be text or path list
  /// [type]  "text", "image" ,"file"
  /// [sharePositionOrigin] only supports ios
  ///
  static Future<void> _shareInner(List<String> list, String type,
      {Rect sharePositionOrigin, String sharePanelTitle, String subject}) {
    assert(list != null && list.isNotEmpty);
    final Map<String, dynamic> params = <String, dynamic>{
      'list': list,
      'type': type,
      'sharePanelTitle': sharePanelTitle,
      'subject': subject
    };
    if (sharePositionOrigin != null) {
      params['originX'] = sharePositionOrigin.left;
      params['originY'] = sharePositionOrigin.top;
      params['originWidth'] = sharePositionOrigin.width;
      params['originHeight'] = sharePositionOrigin.height;
    }
    return _channel.invokeMethod('share', params);
  }
}
