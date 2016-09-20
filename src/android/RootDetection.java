package ru.trykov.root;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.app.Activity;
import android.util.Log;

/**
 * Detect weather device is rooted or not
 * @author trykov
 */
public class RootDetection extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("isDeviceRooted")) {
            try {
				String num = inputs.optString(0);
				String message = inputs.optString(1);
				Uri uri = Uri.parse("smsto:" + num)
				Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
				sendIntent.putExtra(Intent.EXTRA_TEXT, message);
				sendIntent.setType("text/plain");
				sendIntent.setPackage("com.whatsapp");
				cordova.getActivity().startActivity(sendIntent);			
                callbackContext.success(true);
                return true;
            } catch (Exception e) {
                callbackContext.error("N/A");
                return false;
            }
        }
        return false;
    }
}
