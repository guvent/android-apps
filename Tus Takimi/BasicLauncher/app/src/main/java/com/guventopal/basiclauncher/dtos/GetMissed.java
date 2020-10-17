package com.guventopal.basiclauncher.dtos;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.icu.text.TimeZoneFormat;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import com.guventopal.basiclauncher.MainActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GetMissed {
    private Context applicationContext;
    private String TAG = "Contact: ";
    private ArrayList<Contact> contacts = null;
    private GetContact getContact;

    public GetMissed(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.getContact = MainActivity.getContactHandle();
    }

    public ArrayList<Missed> getMissedList() {
        System.out.println("Missed Calls ...........................");
        return getCallDetails(this.applicationContext);
    }


    private ArrayList<Missed> getCallDetails(Context context) {
        ArrayList<Missed> misseds = new ArrayList<>();
        try {

            Uri contentUri = CallLog.Calls.CONTENT_URI;
            Cursor managedCursor = context.getContentResolver().query(
                    contentUri, null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);

            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int type = managedCursor.getColumnIndex(String.valueOf(CallLog.Calls.TYPE));

            while (managedCursor.moveToNext()) {
                String callType = "";

                String cType = managedCursor.getString(type);
                String phNumber = managedCursor.getString(number);
                String callDuration = managedCursor.getString(duration);
                Date callDayTime = new Date(Long.valueOf(managedCursor.getString(date)));

                DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(applicationContext);
                String callByDate = dateFormat.format(callDayTime);

                DateFormat timeFormat = android.text.format.DateFormat.getDateFormat(applicationContext);
                callByDate += " " + timeFormat.format(callDayTime);

                callDuration = calcTimeOfSecond(callDuration);

                switch (Integer.parseInt(cType)) {
                    case CallLog.Calls.INCOMING_TYPE:
                        callType = "incoming";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        callType = "outgoing";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callType = "missing";
                        break;
                    case CallLog.Calls.REJECTED_TYPE:
                        callType = "reject";
                        break;
                    case CallLog.Calls.BLOCKED_TYPE:
                        callType = "blocked";
                        break;
                    case CallLog.Calls.VOICEMAIL_TYPE:
                        callType = "voicemail";
                        break;
                }

                String callerName = getContactName(phNumber);

                Missed missed = new Missed(phNumber);
                missed.setType(callType);
                missed.setName(callerName);
                missed.setTime(callByDate);
                missed.setDuration(callDuration);
                misseds.add(missed);

                System.out.println("**********************************");
                System.out.println(phNumber);

            }
            managedCursor.close();

        } catch (SecurityException e) {
            Log.e("Security Exception", "User denied call log permission");
        }

        return misseds;
    }

    private String calcTimeOfSecond(String duration) {
        int callSecond = Integer.valueOf(duration);

        int hour = 0;
        int minute = 0;
        int second = 0;

        second = (callSecond>=60) ? Math.round(callSecond % 60) : callSecond;

        callSecond = callSecond - second;

        minute = (callSecond>=60) ? Math.round(callSecond / 60) : 0;
        hour = (minute>=60) ? Math.round(minute / 60) : 0;

        String hours = (hour>9) ? Integer.toString(hour) : "0"+ Integer.toString(hour);
        String minutes = (minute>9) ? Integer.toString(minute) : "0"+ Integer.toString(minute);
        String seconds = (second>9) ? Integer.toString(second) : "0"+ Integer.toString(second);

        return hours+":"+minutes+":"+seconds;
    }

    private String getContactName(String number) {
        ArrayList<Contact> contacts = getContact.getContactList();
        for (Contact contact : contacts) {
            if(contact.checkPhones(number)) {
                return contact.getName();
            }
        }
        return number;
    }
}
