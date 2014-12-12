package finalyear.coursework.coc115.mathapplication;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.AppEventsLogger;

import java.util.Calendar;


public class LandingActivity extends ActionBarActivity {

    private PendingIntent pendingIntent;

    @Override
    public void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        //hide action bar for the main screen
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void startButtonClick(View v) {
        Intent intent = new Intent(this, TopicsPageActivity.class);
        startActivity(intent);
    }

    public void onNotification(View v) {
        //get alarm manager
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        //create calendar
        Calendar calendar = Calendar.getInstance();

        //set date
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.YEAR, 2014);
        calendar.set(Calendar.DAY_OF_MONTH, 12);

        //set time
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 11);
        calendar.set(Calendar.SECOND, 11);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        //create pending intent
        Intent myIntent = new Intent(LandingActivity.this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(LandingActivity.this, 0, myIntent, 0);

        //use inexact repeat to take up less system resources
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent);

        Toast.makeText(this, "Repeating Notification Created, staring at " + calendar.getTime(), Toast.LENGTH_LONG).show();
    }

    public void onStopNotification(View v) {
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        //find if null
        if(alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "Notification cancelled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Notification failed to cancel", Toast.LENGTH_SHORT).show();
        }
    }
}
