package com.babyscripts.awsplugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.Context;
import android.widget.Toast;
import com.getbabyscripts.BloodPressurePublisher;
import com.getbabyscripts.DefinedEnvironment;
import com.getbabyscripts.InternalApiService;

public class BabyscriptsAWS extends CordovaPlugin {
    @Override
    public boolean execute(
            String action,
            JSONArray args,
            CallbackContext callbackContext
    ) throws JSONException {
        if ("echo".equals(action)) {
            echo(args.getString(0), callbackContext);
            return true;
        }

        return false;
    }

    private void echo(
            String msg,
            CallbackContext callbackContext
    ) {
        if (msg == null || msg.length() == 0) {
            callbackContext.error("Empty message!");
        } else {
            InternalApiService api = new InternalApiService();
            api.publishBloodPressure(
                    new BloodPressurePublisher(
                            "dijGL0R2mOW94Y6fMlczBPkrnkX8h8woOPngXeG5HFTXlr26EiPB6206RWCcHbvzR",
                            190,
                            80
                    ),
                    DefinedEnvironment.Staging
            );
            Toast.makeText(
                    webView.getContext(),
                    msg,
                    Toast.LENGTH_LONG
            ).show();
            callbackContext.success(msg);
        }
    }
}