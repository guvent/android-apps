package com.guventopal.basiclauncher.dtos;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GetContact {
    private Context applicationContext;
    private String TAG = "Contact: ";
    private ArrayList<Contact> contacts = null;

    public GetContact(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ArrayList<Contact> getContactList() {
        if(contacts!=null) return contacts;

        contacts = new ArrayList<Contact>();
        ContentResolver cr = applicationContext.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.Contacts.DISPLAY_NAME);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Contact contact = new Contact(name);

                try {
                    if(id!=null) {
                        InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(applicationContext.getContentResolver(),
                                ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));
                        if(inputStream!=null) contact.setImage(inputStream);
                        assert inputStream != null;
//                        inputStream.close();
                    }
                } catch (Exception e) { e.printStackTrace(); }

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    while(pCur.moveToNext()) {
                        ArrayList<String> phones = getPhoneFromCur(pCur);

                        for(int i = 0; i<phones.size(); i++) {
                            String phoneNo = phones.get(i);
                            if(!phoneNo.isEmpty()) {
                                phoneNo = phoneNo.replaceAll(" ", "");
                                if(phoneNo.matches("[0-9+*#]+")) {
                                    if(phoneNo.length()>2) {
                                        if(!contact.checkPhones(phoneNo)) {
                                            contact.addPhone(phoneNo);
                                        }
                                    }
                                }
                            }
                        }
                    }
//                    Log.i(TAG, "Name: " + name);
                    contacts.add(contact);
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }

        return contacts;
    }

    public ArrayList<String> getPhoneFromCur(Cursor pCur) {
        ArrayList<String> phones = new ArrayList<>();

        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        String data = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
        String data1 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));
        String data2 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA2));
        String data3 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA3));
        String data4 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA4));
        String data5 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA5));
        String data6 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA6));
        String data7 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA7));
        String data8 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA8));
        String data9 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA9));
        String data10 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA10));
        String data11 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA11));
        String data12 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA12));
        String data13 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA13));
        String data14 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA14));
        String data15 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA15));



        if(phoneNo != null)  phones.add(phoneNo);
        if(data != null)  phones.add(data);
        if(data1 != null)  phones.add(data1);
        if(data2 != null)  phones.add(data2);
        if(data3 != null)  phones.add(data3);
        if(data4 != null)  phones.add(data4);
        if(data5 != null)  phones.add(data5);
        if(data6 != null)  phones.add(data6);
        if(data7 != null)  phones.add(data7);
        if(data8 != null)  phones.add(data8);
        if(data9 != null)  phones.add(data9);
        if(data10 != null)  phones.add(data10);
        if(data11 != null)  phones.add(data11);
        if(data12 != null)  phones.add(data12);
        if(data13 != null)  phones.add(data13);
        if(data14 != null)  phones.add(data14);
        if(data15 != null)  phones.add(data15);

        return phones;
    }
}
