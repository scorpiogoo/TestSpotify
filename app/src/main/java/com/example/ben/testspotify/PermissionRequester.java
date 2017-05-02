package com.example.ben.testspotify;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.HashMap;

/**
 * Created by dave on 4/19/17.
 */

public class PermissionRequester {

    private class PermissionState {
        int requestId;
        boolean haveResult;
        int result;

        public PermissionState(int id, boolean haveres, int res) {
            requestId = id;
            haveResult = haveres;
            result = res;
        }
    }

    private Activity theActivity;
    HashMap<String, PermissionState> permState;
    private int requestId = 1;

    public PermissionRequester(Activity activity) {
        theActivity = activity;
        permState = new HashMap<String, PermissionState>();
    }

    public void request(String requestedPermission) {
        if (ContextCompat.checkSelfPermission(theActivity, requestedPermission) == PackageManager.PERMISSION_GRANTED) {
            permState.put(requestedPermission, new PermissionState(0, true, PackageManager.PERMISSION_GRANTED));
        }
        else
        {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(theActivity,
                    requestedPermission)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                // This functionality is not currently implemented, but would be added here.

            } else {

                // No explanation needed, we can request the permission.
                permState.put(requestedPermission, new PermissionState(requestId, false, 1));

                ActivityCompat.requestPermissions(theActivity,
                        new String[]{requestedPermission},
                        requestId);
            }
        }
    }

   public void storeResult(int requestCode, String requestName, int[] grantResults) {

       PermissionState ps = permState.get(requestName);

       // Update the permission state as long as there isn't a newer request pending.
       if(ps.requestId <= requestCode) {
           ps.haveResult = true;
           ps.result = grantResults[0];
           permState.put(requestName, ps);
       }
    }

    public boolean havePermission(String requestName) {
        boolean rv = false;

        PermissionState ps = permState.get(requestName);
        if(ps.haveResult) {
            if(ps.result == PackageManager.PERMISSION_GRANTED)
                rv = true;
        }
        return rv;
    }

}
