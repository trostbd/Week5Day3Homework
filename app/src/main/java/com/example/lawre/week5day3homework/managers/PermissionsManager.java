package com.example.lawre.week5day3homework.managers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionsManager
{
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 413;
    IPermissionManager iPermissionManager;
    Context context;

    public PermissionsManager(IPermissionManager iPermissionManager, Context context) {
        this.iPermissionManager = iPermissionManager;
        this.context = context;
    }

    public void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, Manifest.permission.READ_CONTACTS))
            {
                //show something explaining why permissions are important
            }
            else
            {
                requestPermission();
            }
        }
        else
        {
            iPermissionManager.onPermissionResult(true);
        }
    }

    public void requestPermission()
    {
        ActivityCompat.requestPermissions((Activity)context,
                new String[]{Manifest.permission.READ_CONTACTS},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
    }

    public void checkResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    iPermissionManager.onPermissionResult(true);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    iPermissionManager.onPermissionResult(false);
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public interface IPermissionManager
    {
        void onPermissionResult(boolean isGranted);
    }

}
