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

  /// method to share with system ui
  ///  It uses the ACTION_SEND Intent on Android and UIActivityViewController
  /// on iOS.
  /// [list] can be text or path list
  /// [type]  "text", "image", "audio", "video" or "file"
  /// [sharePositionOrigin] only supports iPad os
  /// [sharePanelTitle] only supports android (some devices may not support)
  /// [subject] Intent.EXTRA_SUBJECT on Android and "subject" on iOS.
  /// [extraText] only supports android for Intent.EXTRA_TEXT when sharing image or file.
  ///
  static Future<void> shareMultiple(List<String> list, String type,
      {Rect? sharePositionOrigin,
      String? sharePanelTitle,
      String subject = "",
      List<String>? extraTexts}) {
    assert(list.isNotEmpty);
    return _shareInner(list, type,
        sharePositionOrigin: sharePositionOrigin,
        subject: subject,
        sharePanelTitle: sharePanelTitle,
        extraTexts: extraTexts);
  }

  /// method to share with system ui
  ///  It uses the ACTION_SEND Intent on Android and UIActivityViewController
  /// on iOS.
  /// [list] can be text or path list
  /// [type]  "text", "image", "audio", "video" or "file"
  /// [sharePositionOrigin] only supports iPad os
  /// [sharePanelTitle] only supports android (some devices may not support)
  /// [subject] Intent.EXTRA_SUBJECT on Android and "subject" on iOS.
  /// [extraText] only supports android for Intent.EXTRA_TEXT when sharing image or file.
  ///
  static Future<void> share(String text, String type,
      {Rect? sharePositionOrigin,
      String? sharePanelTitle,
      String subject = "",
      String extraText = ""}) {
    assert(text.isNotEmpty);
    List<String> list = [text];
    return _shareInner(
      list,
      type,
      sharePositionOrigin: sharePositionOrigin,
      sharePanelTitle: sharePanelTitle,
      subject: subject,
      extraTexts: [extraText],
    );
  }

  static Future<void> _shareInner(List<String> list, String type,
      {Rect? sharePositionOrigin,
      String? sharePanelTitle,
      String? subject,
      List<String>? extraTexts}) {
    assert(list.isNotEmpty);
    final Map<String, dynamic> params = <String, dynamic>{
      'list': list,
      'type': type,
      'sharePanelTitle': sharePanelTitle,
      'subject': subject,
      'extraTexts': extraTexts
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
