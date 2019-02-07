package com.example.lawre.week5day3homework.managers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import java.util.ArrayList;
import java.util.List;

public class ContactsManager
{
    Context context;
    IContactsManager iContactsManager;
    List<Contact> contacts;

    public ContactsManager(Context context) {
        this.context = context;
        this.iContactsManager = (IContactsManager)context;
        contacts = new ArrayList<>();
    }

    public void getContacts()
    {
        Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Cursor cur = context.getContentResolver().query(
                contactsUri, null,null,null,null);

        while(cur.moveToNext())
        {
            String contactName = cur.getString(cur.getColumnIndex(DISPLAY_NAME));
            int hasNumberColumnIndex = cur.getColumnIndex(HAS_PHONE_NUMBER);
            int has_phone = cur.getInt(hasNumberColumnIndex);
            if(has_phone > 0)
            {
                List<String> numbers = new ArrayList<>();
                Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
                Cursor phoneCur = context.getContentResolver().query(
                        phoneUri, new String[]{NUMBER}, DISPLAY_NAME + "=?", new String[]{contactName},NUMBER + " ASC"
                );
                while(phoneCur.moveToNext())
                {
                    String phoneNumber = phoneCur.getString(phoneCur.getColumnIndex(NUMBER));
                    numbers.add(phoneNumber);
                }
                String userEmail = "";
                Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
                String EMAIL = ContactsContract.CommonDataKinds.Email.ADDRESS;
                String contactId = cur
                        .getString(cur
                                .getColumnIndex(ContactsContract.Contacts._ID));
                Cursor emailCursor = context.getContentResolver().query(
                        emailUri,
                        null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID
                                + " = " + contactId, null, null
                );
                while(emailCursor.moveToNext())
                {
                    String emailAddress = emailCursor.getString(emailCursor.getColumnIndex(EMAIL));
                    userEmail = emailAddress;
                }
                Contact contact = new Contact(contactName,userEmail);
                contacts.add(contact);
            }
        }
        iContactsManager.getContacts(contacts);
    }

    public String getContactEmail(String name)
    {
        for(Contact contact : contacts)
        {
            if(contact.name.equals(name))
            {
                return contact.email;
            }
        }
        return "";
    }

    public interface IContactsManager
    {
        void getContacts(List<Contact> contacts);
    }
}