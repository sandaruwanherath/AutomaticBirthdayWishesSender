package com.totsp.ManageMessageSending;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;


public class MessageToPhone {
	
	//Context conn;
	
	public void sendSMS(String phoneNumber, String message,Activity A,Context co)      //message send to the another phone
    {        
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
 
        PendingIntent sentPI = PendingIntent.getBroadcast(co, 0,new Intent(SENT), 0);
 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(co, 0,new Intent(DELIVERED), 0);
 
  
        SmsManager sms = SmsManager.getDefault();  //get the default instance of the sms manager
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);        //send a Text based SMS
    }

}
