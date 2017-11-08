package projectetaxi.etaxi_v1;

import android.app.NotificationManager;
import android.app.PendingIntent;
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

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            Map<String, String> data = remoteMessage.getData();
            String name = data.get("name");
            String mobileNumer = data.get("mobileNumber");
            String latitude = data.get("latitude");
            String longitude = data.get("longitude");


            Intent intent = new Intent(FirebaseMessagingService.this,
                    NotificationHandlingActivity.class);
            FirebaseMessagingService.this.startActivity(intent);

            Bundle fcmBundle = new Bundle();
            fcmBundle.putString("name", name);
            fcmBundle.putString("mobileNumber", mobileNumer);
            fcmBundle.putString("latitude", latitude);
            fcmBundle.putString("longitude", longitude);
            intent.putExtras(fcmBundle);
            startActivity(intent);

            Log.d(TAG, "XXXXXXXXXXXXXXXXXXXXX: " + remoteMessage.getData().get("name"));
            Log.d(TAG, "String Name:::::::: " + name);



//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                //scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                //handleNow();
//            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        } else {

            Log.d(TAG, "******************** NO NOTIFICATION ******************");
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.














//        Log.d(TAG, "From: "+remoteMessage.getFrom());
//        Log.d(TAG, "Notification Message Body: "+remoteMessage.getNotification().getBody());

        //showNotification(remoteMessage.getData().get("Message"));
    }

//    private void showNotification(String message) {
//
//        Intent intent = new Intent(this, DriverMainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setAutoCancel(true)
//                .setContentTitle("Taxi Booking")
//                .setContentText(message)
//                //.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
//                .setContentIntent(pendingIntent);
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());
//
//    }
}
