package projectetaxi.etaxi_v1;

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

    }
}
