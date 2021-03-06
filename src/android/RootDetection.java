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
				String num = args.optString(0);
				String message = args.optString(1);

				Uri mUri = Uri.parse("smsto:" + num);
				Intent mIntent = new Intent(Intent.ACTION_SENDTO, mUri);
				mIntent.putExtra("sms_body", "The text goes here:" + message);
				mIntent.setPackage("com.whatsapp");
				mIntent.putExtra("text", message);
				setClipboard(this.cordova.getActivity().getApplicationContext(), message);
				mIntent.putExtra("chat",true);
				this.cordova.getActivity().startActivity(mIntent);			
                callbackContext.success(num + ":" + message);
                return true;
            } catch (Exception ex) {				
                callbackContext.error(Log.getStackTraceString(ex));
                return false;
            }
        }
        return false;
    }
	
	private void setClipboard(Context context,String text) {
		if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
			android.text.ClipboardManager clipboard = (android.text.ClipboardManager) this.cordova.getActivity().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
			clipboard.setText(text);
		} else {
			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) this.cordova.getActivity().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
			android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
			clipboard.setPrimaryClip(clip);
		}
	}
}
