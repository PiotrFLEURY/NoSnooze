package piotr.fr.nosnooze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

import piotr.fr.nosnooze.dataobjects.Alarm;

/**
 * Created by Piotr on 07/02/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Alarm alarm = Configuration.load(context);

        AlarmExecutor.INSTANCE.startAlarm(context, alarm);

        Intent alarmIntent = new Intent(context, AlarmActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);

        AlarmScheduler.schedule(context, Configuration.load(context));
    }
}
