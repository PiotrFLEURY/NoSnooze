package piotr.fr.nosnooze;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.text.MessageFormat;

import piotr.fr.nosnooze.dataobjects.Enigma;


public class AlarmActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        final Animation animationScaleInfinite = AnimationUtils.loadAnimation(this, R.anim.anim_scale_infinite);
        final Animation animationScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        final Animation animationRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        final ImageView brokenCircle = (ImageView) findViewById(R.id.brokencircle);

        ImageView disk = (ImageView)findViewById(R.id.disk);
        disk.startAnimation(animationScaleInfinite);

        Button button = (Button) findViewById(R.id.animatedButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                brokenCircle.startAnimation(animationRotate);
                Toast.makeText(AlarmActivity.this, getString(R.string.appuyezlongtemps), Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                brokenCircle.startAnimation(animationRotate);
                return false;
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.startAnimation(animationScale);
                int hazadus = (int) (Math.random()*2);
                switch (hazadus){
                    case 0:
                        startActivity(new Intent(AlarmActivity.this, CirclesActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(AlarmActivity.this, ShapesActivity.class));
                        break;
                    default:
                        //
                }
                finish();
                return true;
            }
        });

        setVolumeControlStream(AudioManager.STREAM_ALARM);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }

    @Override
    public void onBackPressed() {
        //
    }
}
