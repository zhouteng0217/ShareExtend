package com.zt.shareextend;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;


import java.io.File;
import java.util.Map;

/** Plugin method host for presenting a share sheet via Intent */
public class ShareExtendPlugin implements MethodChannel.MethodCallHandler {

  private static final String CHANNEL = "share_extend";

  public static void registerWith(Registrar registrar) {
    MethodChannel channel = new MethodChannel(registrar.messenger(), CHANNEL);
    ShareExtendPlugin instance = new ShareExtendPlugin(registrar);
    channel.setMethodCallHandler(instance);
  }

  private final Registrar mRegistrar;

  private ShareExtendPlugin(Registrar registrar) {
    this.mRegistrar = registrar;
  }

  @Override
  public void onMethodCall(MethodCall call, MethodChannel.Result result) {
    if (call.method.equals("share")) {
      if (!(call.arguments instanceof Map)) {
        throw new IllegalArgumentException("Map argument expected");
      }
      // Android does not support showing the share sheet at a particular point on screen.
      share((String) call.argument("text"),(String) call.argument("type"),(String) call.argument("authorities"));
      result.success(null);
    } else {
      result.notImplemented();
    }
  }

  private void share(String text, String type,String authorities) {
    if (text == null || text.isEmpty()) {
      throw new IllegalArgumentException("Non-empty text expected");
    }

    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);

    if ("text".equals(type)) {
      shareIntent.putExtra(Intent.EXTRA_TEXT, text);
      shareIntent.setType("text/plain");
    } else {
      File f = new File(text);
      Uri uri = getUriForFile(mRegistrar.context(), authorities,f);
      if ("file".equals(type)) {
        shareIntent.setType("application/*");
      }
      if ("image".equals(type)) {
        shareIntent.setType("image/*");
      }
      shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
    }

    Intent chooserIntent = Intent.createChooser(shareIntent, null /* dialog title optional */);
    if (mRegistrar.activity() != null) {
      mRegistrar.activity().startActivity(chooserIntent);
    } else {
      chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      mRegistrar.context().startActivity(chooserIntent);
    }
  }

  public static Uri getUriForFile(Context context, String authories, File file) {
    if (Build.VERSION.SDK_INT >= 24) {
      return FileProvider.getUriForFile(context, authories, file);
    } else {
      return Uri.fromFile(file);
    }
  }
}
