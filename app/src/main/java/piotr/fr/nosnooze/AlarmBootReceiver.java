package piotr.fr.nosnooze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import piotr.fr.nosnooze.dataobjects.Alarm;

/**
 * Created by Piotr on 07/02/2015.
 */
public class AlarmBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Alarm alarm = Configuration.load(context);
        if(alarm.isActivated()){
            AlarmScheduler.schedule(context, alarm);
        }
    }
}
