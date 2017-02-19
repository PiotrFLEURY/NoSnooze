package piotr.fr.nosnooze;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;

import piotr.fr.nosnooze.dataobjects.Alarm;

/**
 * Created by Piotr on 08/02/2015.
 */
public class AlarmExecutor {

    private MediaPlayer mMediaPlayer;
    private Vibrator v;

    public static AlarmExecutor INSTANCE=new AlarmExecutor();

    private void startRingTone(Context context, Alarm alarm){
        try {
            Uri alert = Uri.parse(alarm.getSelectedRingtone());
                    //RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Une erreur est survenue", Toast.LENGTH_SHORT);
        }
    }

    private void stopRingTone(){
        if(mMediaPlayer!=null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    private void startVibrate(Context context){
        // Get instance of Vibrator from current Context
        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        // Start without a delay
        // Vibrate for 500 milliseconds
        // Sleep for 2000 milliseconds
        long[] pattern = {0, 500, 1000};

        // The '0' here means to repeat indefinitely
        // '0' is actually the index at which the pattern keeps repeating from (the start)
        // To repeat the pattern from any other point, you could increase the index, e.g. '1'
        v.vibrate(pattern, 0);
    }

    private void stopVibrate(){
        if(v!=null) {
            v.cancel();
        }
    }

    public void stopAlarm(){
        stopRingTone();
        stopVibrate();
    }

    public void startAlarm(Context context, Alarm alarm) {
        startRingTone(context, alarm);
        startVibrate(context);
    }
}
