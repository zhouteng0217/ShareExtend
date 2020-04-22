package com.zt.shareextend;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.util.List;

class ShareUtils {

    /// get the uri for file
    static Uri getUriForFile(Context context, File file) {

        String authorities = context.getPackageName() + ".shareextend.fileprovider";

        return ShareExtendProvider.getUriForPath(authorities, file.getAbsolutePath());
    }

    static void grantUriPermission(Context context, List<Uri> uriList, Intent intent) {
        // some devices will crash when calling queryIntentActivities, so add try catch temporarily.
        try {
            for (Uri uri : uriList) {
                List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resolveInfos) {
                    context.grantUriPermission(resolveInfo.activityInfo.packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
            }
        } catch (Exception e) {

        }
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
