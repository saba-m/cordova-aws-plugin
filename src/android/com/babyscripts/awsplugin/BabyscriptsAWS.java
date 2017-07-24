package com.babyscripts.awsplugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.Context;
import android.widget.Toast;
/*
import com.getbabyscripts.BloodPressurePublisher;
import com.getbabyscripts.DefinedEnvironment;
import com.getbabyscripts.InternalApiService;
*/

public class BabyscriptsAWS extends CordovaPlugin {
    @Override
    public boolean execute(
            String action,
            JSONArray args,
            CallbackContext callbackContext
    ) throws JSONException {
        if ("echo".equals(action)) {
            echo(args.getString(0),args.getString(1),args.getString(2),args.getString(3), callbackContext);
            return true;
        }

        return false;
    }

    private void echo(
            String sys,String diasys,String accessToken,String environment,
            CallbackContext callbackContext
    ) {
       /* if (msg == null || msg.length() == 0) {
            callbackContext.error("Empty message!");
        } else {*/
        System.out.println("calling the api ");
        InternalApiService api = new InternalApiService();
        DefinedEnvironment env = DefinedEnvironment.Prod;
        if(environment.equals("staging")){
            env = DefinedEnvironment.Staging;
        }else if(environment.equals("demo")){
            env = DefinedEnvironment.Demo;
        }
        api.publishBloodPressure(
                new BloodPressurePublisher(
                        accessToken,
                        Integer.parseInt(sys),
                        Integer.parseInt(diasys)
                ),
                env
        );
            /*Toast.makeText(
                    webView.getContext(),
                    msg,
                    Toast.LENGTH_LONG
            ).show();*/
        callbackContext.success(accessToken);
        //}
    }
}
