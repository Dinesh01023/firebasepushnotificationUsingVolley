package pcn.action.sunichith.developer.firebasepushnotification.Firebase;

import android.app.PendingIntent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import pcn.action.sunichith.developer.firebasepushnotification.MainActivity;
import pcn.action.sunichith.developer.firebasepushnotification.Myapplication;
import pcn.action.sunichith.developer.firebasepushnotification.R;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

   String  TAG ="MyFirebaseMessagingService";
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

         message.getNotification().getTitle();
         message.getNotification().getBody();
         Log.e(TAG,message.getFrom());
        Log.e(TAG, message.getNotification().getBody());
        ((Myapplication)getApplication()).triggerNotificationWithBackStack(MainActivity.class,
                getString(R.string.NEWS_CHANNEL_ID),
                message.getNotification().getTitle(), "89",
                        message.getNotification().getBody(),
                NotificationCompat.PRIORITY_HIGH,
                false,
                Integer.parseInt("89"),
                PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);

    }

}
