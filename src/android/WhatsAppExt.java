package com.whatsapp.ext.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.app.Activity;

public class WhatsAppExt extends CordovaPlugin {

   
    private enum Command {
		SEND, READ, ERROR;
	};
	
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		JSONObject retval = new JSONObject();
		Command cmd = Command.ERROR;
		
    	try{
    		cmd = Command.valueOf(action);
    	}catch(IllegalArgumentException iae){}
    	
    	switch(cmd){
    		case SEND:
				String to = args.optString(0);
				String message = args.optString(1);
				String error = sendWhatsAppMsg(to, message);
				if(error.length() > 0)
					retval.put("message", error);
				else
					retval.put("code", StatusCode.SUCCESS_MSG_SENT);
				break;
			case READ:
    			retval.put("code", StatusCode.SUCCESS_MSG_READ);
				break;
			default:		
				retval.put("message", "Invalid command! Please try again.");
				retval.put("code", StatusCode.INVALID_CMD);
		   		callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, retval));
		        return false;
    	}        
   		callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, retval));
        return true;
    }
    
    public String sendWhatsAppMsg(String to, String message) {	
		try{
			Uri uri = Uri.parse("smsto:" + to);
			Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
			sendIntent.putExtra(Intent.EXTRA_TEXT, message);
			sendIntent.setType("text/plain");
			sendIntent.setPackage("com.whatsapp");		
			cordova.getActivity().startActivity(sendIntent);
		}catch(Exception ex){			
			return Log.getStackTraceString(ex); 
		}
		return "";
	}
}

class StatusCode{
	public static final byte INVALID_CMD = 0x0;
	public static final byte SUCCESS_MSG_SENT = 0x1;
	public static final byte SUCCESS_MSG_READ = 0x2;
	public static final byte FAILED_MSG_SENT = 0x3;
	public static final byte FAILED_MSG_READ = 0x4;
}