package com.zt.shareextend;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * Plugin method host for presenting a share sheet via Intent
 */
public class ShareExtendPlugin implements FlutterPlugin, ActivityAware, PluginRegistry.RequestPermissionsResultListener {

    /// the authorities for FileProvider
    private static final int CODE_ASK_PERMISSION = 100;
    private static final String CHANNEL = "com.zt.shareextend/share_extend";

    private FlutterPluginBinding pluginBinding;
    private ActivityPluginBinding activityBinding;

    private MethodChannel methodChannel;
    private MethodCallHandlerImpl callHandler;
    private Share share;

    public static void registerWith(Registrar registrar) {
        ShareExtendPlugin plugin = new ShareExtendPlugin();
        plugin.setUpChannel(registrar.context(), registrar.messenger(), registrar, null);
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        pluginBinding = flutterPluginBinding;
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        pluginBinding = null;
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        activityBinding = activityPluginBinding;
        setUpChannel(activityBinding.getActivity(), pluginBinding.getBinaryMessenger(), null, activityBinding);
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
        onAttachedToActivity(activityPluginBinding);
    }

    @Override
    public void onDetachedFromActivity() {
        tearDown();
    }

    private void setUpChannel(Context context, BinaryMessenger messenger,Registrar registrar, ActivityPluginBinding activityBinding) {
        methodChannel = new MethodChannel(messenger, CHANNEL);
        share = new Share(context);
        callHandler = new MethodCallHandlerImpl(share);
        methodChannel.setMethodCallHandler(callHandler);
        if (registrar != null) {
            registrar.addRequestPermissionsResultListener(this);
        } else {
            activityBinding.addRequestPermissionsResultListener(this);
        }
    }

    private void tearDown() {
        activityBinding.removeRequestPermissionsResultListener(this);
        activityBinding = null;
        methodChannel.setMethodCallHandler(null);
        methodChannel = null;
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] perms, int[] grantResults) {
        if (requestCode == CODE_ASK_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            share.share();
        }
        return false;
    }
}
