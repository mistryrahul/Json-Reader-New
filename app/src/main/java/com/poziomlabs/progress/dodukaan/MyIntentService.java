package com.poziomlabs.progress.dodukaan;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Toast.makeText(getApplicationContext(), "Alarm Set", Toast.LENGTH_LONG).show();


        int NOTIFY_ID = 1;

        String title = "Come down asap";
        String text = "We are waiting for you just at your place";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.oe_logo).setContentTitle(title).setContentText(text);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID,notification);


    }

}
