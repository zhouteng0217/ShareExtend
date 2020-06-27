package com.zt.shareextend;

import androidx.annotation.NonNull;

import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * Created by zhouteng on 2020/6/27
 */
public class MethodCallHandlerImpl implements MethodChannel.MethodCallHandler {

    private static final String METHOD_SHARE = "share";

    private Share share;

    public MethodCallHandlerImpl(Share share) {
        this.share = share;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (METHOD_SHARE.equals(call.method)) {
            if (!(call.arguments instanceof Map)) {
                throw new IllegalArgumentException("Map argument expected");
            }
            share.share(call);
            result.success(null);
        } else {
            result.notImplemented();
        }
    }

}
