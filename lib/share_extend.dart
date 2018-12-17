/// A flutter plugin to share text, image, file with system ui.
/// It is compatible with both andorid and ios.
///
///
/// A open source authorized by zhouteng [https://github.com/zhouteng0217/ShareExtend](https://github.com/zhouteng0217/ShareExtend).

import 'dart:async';
import 'package:flutter/services.dart';
import 'dart:ui';

/// Plugin for summoning a platform share sheet.
class ShareExtend {
  /// [MethodChannel] used to communicate with the platform side.
  static const MethodChannel _channel = const MethodChannel('share_extend');

  /// method to share with system ui
  ///  It uses the ACTION_SEND Intent on Android and UIActivityViewController
  /// on iOS.
  /// type  "text", "image" ,"file"
  ///
  static Future<void> share(String text, String type,
      {Rect sharePositionOrigin}) {
    assert(text != null);
    assert(text.isNotEmpty);
    final Map<String, dynamic> params = <String, dynamic>{
      'text': text,
      'type': type
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
