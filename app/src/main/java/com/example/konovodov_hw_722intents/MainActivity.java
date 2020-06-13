package com.example.konovodov_hw_722intents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText messageField;
    EditText phoneNumberField;
    Button callButton;
    Button sendButton;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 11;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageField = findViewById(R.id.messageField);
        phoneNumberField = findViewById(R.id.phoneNumberField);
        callButton = findViewById(R.id.callButton);
        sendButton = findViewById(R.id.sendButton);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callByNumber();
            }


        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageByNumber();

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // Проверяем результат запроса на право позвонить
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Разрешение получено, осуществляем звонок
                    callByNumber();
                } else {
                    // Разрешение не дано. Закрываем приложение
                    finish();
                }
            }
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                // Проверяем результат запроса на право позвонить
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Разрешение получено, осуществляем отправку SMS
                    sendMessageByNumber();
                } else {
                    // Разрешение не дано. Закрываем приложение
                    finish();
                }
            }
        }
    }


    private void callByNumber() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Разрешение не получено
            // Делаем запрос на добавление разрешения звонка
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            // Разрешение уже получено
            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumberField.getText().toString()));
            // Звоним
            startActivity(dialIntent);
        }
    }

    @SuppressLint("IntentReset")
    private void sendMessageByNumber() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);

        } else {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:"));
            intent.setType("vnd.android-dir/mms-sms");

            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(phoneNumberField.getText().toString(), null, messageField.getText().toString(), null, null);
            Toast.makeText(this, "Сообщение отправлено", Toast.LENGTH_SHORT).show();
        }

    }


}

