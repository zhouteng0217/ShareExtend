package com.zt.shareextendexample;

import android.os.Bundle;

import com.vitanov.multiimagepicker.MultiImagePickerPlugin;
import com.zt.shareextend.ShareExtendPlugin;

import io.flutter.app.FlutterActivity;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.plugins.flutter_plugin_android_lifecycle.FlutterAndroidLifecyclePlugin;
import io.flutter.plugins.imagepicker.ImagePickerPlugin;
import io.flutter.plugins.pathprovider.PathProviderPlugin;

public class EmbeddingV1Activity extends FlutterActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ShareExtendPlugin.registerWith(registrarFor("com.zt.shareextend.ShareExtendPlugin"));
    ImagePickerPlugin.registerWith(registrarFor("io.flutter.plugins.imagepicker.ImagePickerPlugin"));
    MultiImagePickerPlugin.registerWith(registrarFor("com.vitanov.multiimagepicker.MultiImagePickerPlugin"));
    PathProviderPlugin.registerWith(registrarFor("io.flutter.plugins.pathprovider.PathProviderPlugin"));
    FlutterAndroidLifecyclePlugin.registerWith(registrarFor("io.flutter.plugins.flutter_plugin_android_lifecycle.FlutterAndroidLifecyclePlugin"));
  }
}
