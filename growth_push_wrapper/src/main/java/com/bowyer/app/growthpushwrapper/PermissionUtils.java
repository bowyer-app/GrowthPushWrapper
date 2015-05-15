package com.bowyer.app.growthpushwrapper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class PermissionUtils {

    public static boolean permitted(Context context, String permission) {

        PackageInfo packageInfo = null;

        try {
            packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

        String[] requestedPermissions = packageInfo.requestedPermissions;

        for (String requestedPermission : requestedPermissions) {
            if (requestedPermission.equals(permission)) {
                return true;
            }
        }

        return false;

    }

}
