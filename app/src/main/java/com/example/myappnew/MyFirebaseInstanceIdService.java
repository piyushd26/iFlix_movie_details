package com.example.myappnew;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.myappnew.Activities.MainActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseInstanceIdService extends FirebaseMessagingService {



    private static final String TAG = "fcmexamplemessege";

    public static final String TOKEN_BROADCAST="myfcmtokenbroadcast";
    @Override
    public void onNewToken(String Refreshedtoken) {
        Log.d("myFirebaseId", "Refreshed token: " + Refreshedtoken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);

        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));
        storeToken(Refreshedtoken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "id")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New")
                .setContentText("Msesss")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //  scheduleJob();
            } else {
                // Handle message within 10 seconds
                //  handleNow();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    //  notifyUser(remoteMessage.getFrom(),remoteMessage.getNotification().getBody());

    }




    private void storeToken(String token) {

        SharedPrefManager.getInstance(getApplicationContext()).storetoken(token);

    }

    public void notifyUser(String from,String notification){

        MyNotificationManager myNotificationManager =new MyNotificationManager(getApplicationContext());
    myNotificationManager.showNotification(from,notification,new Intent(getApplicationContext(), MainActivity.class));
    }
}
