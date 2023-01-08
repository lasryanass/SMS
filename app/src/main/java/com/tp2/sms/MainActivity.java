package com.tp2.sms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    EditText messageTexte;
    String phoneNo;
    String message;
    Button boutonEnvoi;
    EditText numeroTelephone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageTexte = (EditText) findViewById(R.id.editText);
        boutonEnvoi = (Button) findViewById(R.id.button);
        numeroTelephone = (EditText) findViewById(R.id.editText2);
        boutonEnvoi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                envoyerSMS();
            }
        });
    }
    protected void envoyerSMS() {
        phoneNo = numeroTelephone.getText().toString();
        message = messageTexte.getText().toString();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager gestionnaireSMS = SmsManager.getDefault();
                    gestionnaireSMS.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS envoye ! :-)", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"SMS pas envoye", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}