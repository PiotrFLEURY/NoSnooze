package piotr.fr.nosnooze;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.Calendar;

import piotr.fr.nosnooze.dataobjects.Alarm;

/**
 * Created by Piotr on 08/02/2015.
 */
public class AlarmScheduler {

    private static final AlarmScheduler INSTANCE = new AlarmScheduler();

    public static final int ONE_DAY_INTERVAL = 1000 * 60 * 60 * 24;

    private static PendingIntent intent(Context context){
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        return alarmIntent;
    }

    public static void schedule(Context context, Alarm alarm){
        cancel(context);
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = intent(context);
        Calendar calendar = alarm.toCalendar();
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

    }

    public static void cancel(Context context){
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = intent(context);
        alarmMgr.cancel(alarmIntent);

    }
}
