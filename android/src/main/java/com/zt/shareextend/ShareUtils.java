package com.zt.shareextend;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.util.List;

class ShareUtils {

    /// get the uri for file
    static Uri getUriForFile(Context context, File file) {

        String authorities = context.getPackageName() + ".shareextend.fileprovider";

        Uri uri;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {
            uri = ShareExtendProvider.getUriForPath(authorities, file.getAbsolutePath());
        }

        return uri;
    }


    static boolean shouldRequestPermission(List<String> pathList) {
        for (String path : pathList) {
            if (shouldRequestPermission(path)) {
                return true;
            }
        }
        return false;
    }

    private static boolean shouldRequestPermission(String path) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isPathInExternalStorage(path);
    }

    private static boolean isPathInExternalStorage(String path) {
        File storagePath = Environment.getExternalStorageDirectory();
        return path.startsWith(storagePath.getAbsolutePath());
    }
}
