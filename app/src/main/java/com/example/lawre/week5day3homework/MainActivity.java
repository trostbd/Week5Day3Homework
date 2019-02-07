package com.example.lawre.week5day3homework;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lawre.week5day3homework.managers.Contact;
import com.example.lawre.week5day3homework.managers.ContactsManager;
import com.example.lawre.week5day3homework.managers.PermissionsManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PermissionsManager.IPermissionManager, ContactsManager.IContactsManager
{
    PermissionsManager permissionsManager;
    TextView tvInstruction;
    EditText etInputContact;
    Button btSubmit;
    ContactsManager contactsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInstruction = findViewById(R.id.tvInstruction);
        etInputContact = findViewById(R.id.etInputContact);
        btSubmit = findViewById(R.id.btSubmit);
        permissionsManager = new PermissionsManager(this,this);
        permissionsManager.checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.checkResult(requestCode,permissions,grantResults);
    }

    @Override
    public void onPermissionResult(boolean isGranted)
    {
        Log.d("TAG_", "onPermissionResult: " + (isGranted ? "GRANTED" : "SLEEP BRAINY SLEEP FOREVER"));
        if(isGranted)
        {
            getContacts();
        }
        else
        {
            Toast.makeText(this,"YOU FOOL", Toast.LENGTH_LONG).show();
            try {
                wait(Toast.LENGTH_LONG);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast.makeText(this,"DO YOU HAVE ANY IDEA WHAT YOU'VE DONE?", Toast.LENGTH_LONG).show();
        }
    }

    void getContacts()
    {
        contactsManager = new ContactsManager(this);
        contactsManager.getContacts();
    }

    @Override
    public void getContacts(List<Contact> contacts)
    {
        for(Contact contact : contacts)
        {
            Log.d("TAG_", "getContacts: " + contact.toString());
        }
    }

    public void onClick(View view)
    {
        if(view.getId() == R.id.btSubmit && contactsManager != null)
        {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            String email = contactsManager.getContactEmail(etInputContact.getText().toString());
            if(email != "")
            {
                emailIntent.setData(Uri.parse("mailto:"+email));
                startActivity(emailIntent);
            }
        }
    }
}
