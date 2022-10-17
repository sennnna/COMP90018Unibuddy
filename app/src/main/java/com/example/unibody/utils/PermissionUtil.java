package com.example.unibody.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

public class PermissionUtil {

    public static boolean checkPermissions(String[] permissions, Activity activity,int requestCode){
        if (permissions == null || permissions.length == 0){
            return true;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    //请求权限
                    activity.requestPermissions(permissions,requestCode);
                    return false;
                }
            }
        }
        return true;
    }

}
