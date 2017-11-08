package projectetaxi.etaxi_v1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Ashim Bazracharya on 11/1/2017.
 */

public class FirebaseMessagingService extends
        com.google.firebase.messaging.FirebaseMessagingService {

    final String TAG = this.getClass().getName();

    private String name, mobileNumber, longitude, latitude;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {

            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            Map<String, String> data = remoteMessage.getData();
            name = data.get("name");
            mobileNumber = data.get("mobileNumber");
            latitude = data.get("latitude");
            longitude = data.get("longitude");


            Intent intent = new Intent(FirebaseMessagingService.this,
                    NotificationHandlingActivity.class);
            FirebaseMessagingService.this.startActivity(intent);

            Bundle fcmBundle = new Bundle();
            fcmBundle.putString("name", name);
            fcmBundle.putString("mobileNumber", mobileNumber);
            fcmBundle.putString("latitude", latitude);
            fcmBundle.putString("longitude", longitude);
            intent.putExtras(fcmBundle);
            startActivity(intent);

            Log.d(TAG, "XXXXXXXXXXXXXXXXXXXXX: " + remoteMessage.getData().get("name"));
            Log.d(TAG, "String Name:::::::: " + name);

        }

        if (remoteMessage.getNotification() != null) {

            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }
    }
}