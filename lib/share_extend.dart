import 'dart:async';
import 'package:flutter/services.dart';
import 'dart:ui';

class ShareExtend {
  static const MethodChannel _channel = const MethodChannel('share_extend');

  static Future<void> share(String text, String type, String authorities,
      {Rect sharePositionOrigin}) {
    assert(text != null);
    assert(text.isNotEmpty);
    final Map<String, dynamic> params = <String, dynamic>{
      'text': text,
      'type': type,
      'authorities': authorities
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
