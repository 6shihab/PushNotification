package com.example.pushnotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String CHANNEL_ID="My Channel";
    private static final int NOTIFICATION_ID=100;
    Button button;
    EditText titleEditText,messageEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.buttonID);
        titleEditText=findViewById(R.id.titleEditText);
        messageEditText=findViewById(R.id.messageEditText);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        notificationSender();
    }

    private void notificationSender() {

        Drawable drawable= ResourcesCompat.getDrawable(getResources(),R.drawable.notificationicon,null);
        BitmapDrawable bitmapDrawable=(BitmapDrawable) drawable;
        Bitmap largeIcon=bitmapDrawable.getBitmap();


        String title= titleEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();


        Intent intent=new Intent(this,welcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);


        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification= new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.notificationicon)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSubText("New Message From App")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID)
                    .build();
            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"New Channel",NotificationManager.IMPORTANCE_HIGH));
        }else {
            notification= new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.notificationicon)
                    .setContentText("New Message")
                    .setSubText("New Message From App")
                    .build();
        }
        notificationManager.notify(NOTIFICATION_ID,notification);
    }
}