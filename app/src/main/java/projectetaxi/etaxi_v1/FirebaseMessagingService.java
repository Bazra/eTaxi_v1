package projectetaxi.etaxi_v1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Ashim Bazracharya on 11/1/2017.
 */

public class FirebaseMessagingService extends
        com.google.firebase.messaging.FirebaseMessagingService {

    final String TAG = this.getClass().getName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: "+remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: "+remoteMessage.getNotification().getBody());

        showNotification(remoteMessage.getData().get("Message"));
    }

    private void showNotification(String message) {

        Intent intent = new Intent(this, DriverMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("FCM Test")
                .setContentText(message)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }
}
