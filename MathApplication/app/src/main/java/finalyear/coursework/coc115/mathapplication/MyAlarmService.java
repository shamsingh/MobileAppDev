package finalyear.coursework.coc115.mathapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;

/**
 * Created by Mark on 12/12/2014.
 */
public class MyAlarmService extends Service {

    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        //create notification manager
        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), LandingActivity.class);

        // create initial notification alert
        Notification notification = new Notification(R.drawable.icon, "Hey, it's been a while...", System.currentTimeMillis());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //get the pending intent and add the notification
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.setLatestEventInfo(this.getApplicationContext(), "It's been a while!", "Why not give some Maths a go?", pendingNotificationIntent);

        mManager.notify(0, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
