package com.google.android.gms.samples.vision.face.facetracker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import com.android.internal.telephony.ITelephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

public class PhoneCallReceiver extends BroadcastReceiver {
    Context context = null;
    private static final String TAG = "Phone call";;
    private ITelephony telephonyService;
    private String number;

    @Override
    public void onReceive(Context context, Intent intent) {


        Log.v(TAG, "Receving....");
        TelephonyManager telephony = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);

        number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        Log.d("number", number);
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.d(TAG ,"STATE:"+ state);

        if (state.equals("RINGING")) {
            try {
                Class c = Class.forName(telephony.getClass().getName());
                Method m = c.getDeclaredMethod("getITelephony");
                m.setAccessible(true);
                String statenow = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                Log.d(TAG, "STATENOW:" + statenow);
                String outString = "I am driving, I will call you back later";
                telephonyService = (ITelephony) m.invoke(telephony);
                telephonyService.endCall();
                respond(number, outString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void respond(String to, String response) {
//        lock.lock();
//        aa.notifyDataSetChanged();
//        lock.unlock();
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(to, null, response, null, null);
    }

}
