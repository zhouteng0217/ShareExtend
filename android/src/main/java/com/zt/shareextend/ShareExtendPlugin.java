package com.zt.shareextend;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * Plugin method host for presenting a share sheet via Intent
 */
public class ShareExtendPlugin implements MethodChannel.MethodCallHandler, PluginRegistry.RequestPermissionsResultListener {

    /// the authorities for FileProvider
    private static final int CODE_ASK_PERMISSION = 100;
    private static final String CHANNEL = "com.zt.shareextend/share_extend";

    private final Registrar mRegistrar;
    private List<String> list;
    private String type;
    private String sharePanelTitle;
    private String subject;

    public static void registerWith(Registrar registrar) {
        MethodChannel channel = new MethodChannel(registrar.messenger(), CHANNEL);
        final ShareExtendPlugin instance = new ShareExtendPlugin(registrar);
        registrar.addRequestPermissionsResultListener(instance);
        channel.setMethodCallHandler(instance);
    }


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
            list = call.argument("list");
            type = call.argument("type");
            sharePanelTitle = call.argument("sharePanelTitle");
            subject = call.argument("subject");
            share(list, type, sharePanelTitle, subject);
            result.success(null);
        } else {
            result.notImplemented();
        }
    }

    private void share(List<String> list, String type, String sharePanelTitle, String subject) {
        ArrayList<Uri> uriList = new ArrayList<>();;

        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Non-empty list expected");
        }
        Intent shareIntent = new Intent();
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);

        if ("text".equals(type)) {
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, list.get(0));
            shareIntent.setType("text/plain");
        } else {
            if (ShareUtils.shouldRequestPermission(list)) {
                if (!checkPermission()) {
                    requestPermission();
                    return;
                }
            }

            for (String path : list) {
                File f = new File(path);
                Uri uri = ShareUtils.getUriForFile(getContext(), f);
                uriList.add(uri);
            }

            if ("image".equals(type)) {
                shareIntent.setType("image/*");
            } else if ("video".equals(type)) {
                shareIntent.setType("video/*");
            } else {
                shareIntent.setType("application/*");
            }
            shareIntent.putExtra(Intent.EXTRA_TEXT, type);
            if (uriList.size() == 1) {
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uriList.get(0));
            } else {
                shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
            }
        }
        startChooserActivity(shareIntent, sharePanelTitle, uriList);
    }

    private Context getContext() {
        return mRegistrar.activity() != null ? mRegistrar.activity() : mRegistrar.context();
    }

    private void startChooserActivity(Intent shareIntent, String sharePanelTitle, ArrayList<Uri> uriList) {
        Intent chooserIntent = Intent.createChooser(shareIntent, sharePanelTitle);
        ShareUtils.grantUriPermission(getContext(), uriList, chooserIntent);
 
        if (mRegistrar.activity() != null) {
            mRegistrar.activity().startActivity(chooserIntent);
        } else {
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mRegistrar.context().startActivity(chooserIntent);
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(mRegistrar.context(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(mRegistrar.activity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_ASK_PERMISSION);
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] perms, int[] grantResults) {
        if (requestCode == CODE_ASK_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            share(list, type, sharePanelTitle, subject);
        }
        return false;
    }
}
