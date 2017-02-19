package piotr.fr.nosnooze;

import android.animation.Animator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.MessageFormat;
import java.util.Calendar;

import piotr.fr.nosnooze.dataobjects.Alarm;
import piotr.fr.nosnooze.dataobjects.FeedBackAnimation;


public class MainActivity extends ActionBarActivity implements TimePickerDialog.OnTimeSetListener{

    public static final int RINGTONE_SELECTION_TASK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setVolumeControlStream(AudioManager.STREAM_ALARM);

        initDayPicker();

    }

    private void initDayPicker() {
        Alarm alarm = Configuration.load(MainActivity.this);
        GridLayout daysLayout = (GridLayout)findViewById(R.id.dayslayout);
        for(int i= Calendar.SUNDAY;i<=Calendar.SATURDAY;i++){
            final Button dayButton = (Button) getLayoutInflater().inflate(R.layout.daycircularbutton, daysLayout, false);
            String dayLabel = DayHelper.toString(this, i);
            dayButton.setText(""+dayLabel.charAt(0));
            dayButton.setTag(i);
            dayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Alarm alarm = Configuration.load(MainActivity.this);
                    int currentDay= (int) dayButton.getTag();
                    dayButton.setSelected(!dayButton.isSelected());
                    if(dayButton.isSelected()){
                        dayButton.setTextColor(Color.WHITE);
                        alarm.addDay(currentDay);
                    } else {
                        dayButton.setTextColor(Color.BLACK);
                        alarm.removeDay(currentDay);
                    }
                    Configuration.save(MainActivity.this, alarm);

                }
            });
            if(alarm.isDaySelected(i)) {
                dayButton.setSelected(true);
                dayButton.setTextColor(Color.WHITE);
            }

            daysLayout.addView(dayButton);

        }
    }

    private void updateSelectedRingtone(Alarm alarm) {
        TextView selectedRingtoneText = (TextView)findViewById(R.id.selectedRingToneText);
        Uri selectedRingtone = alarm.getSelectedRingtone()==null?null:Uri.parse(alarm.getSelectedRingtone());
        if(selectedRingtone==null){
            selectedRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(this, selectedRingtone);
        String ringtoneName = ringtone.getTitle(this);
        selectedRingtoneText.setText(ringtoneName);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Alarm alarm = Configuration.load(this);

        TextView alarmValue = (TextView) findViewById(R.id.alarmValue);
        alarmValue.setText(alarm.toString());

        final Switch switcher = (Switch)findViewById(R.id.alarmSwitcher);
        switcher.setChecked(alarm.isActivated());
        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toogleAlarm(switcher);
            }
        });

//        StringBuffer sb = new StringBuffer();
//        for(Integer dayNumber:alarm.getDays()){
//            if(!sb.toString().isEmpty()){
//                sb.append(", ");
//            }
//            sb.append(DayHelper.toString(this, dayNumber));
//        }
//        TextView daysText = (TextView)findViewById(R.id.daysText);
//        String selectedDays = sb.toString();
//        if(selectedDays.isEmpty()){
//            selectedDays = getString(R.string.action_days);
//        }
//        daysText.setText(selectedDays);

        updateSelectedRingtone(alarm);

        updateNextAlarmText(findViewById(R.id.nextAlarm), alarm);
    }

    @Override
    protected void onPause() {
        super.onPause();
        final Switch switcher = (Switch)findViewById(R.id.alarmSwitcher);
        switcher.setOnCheckedChangeListener(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_hour) {
            changeTime(findViewById(R.id.alarmValue));
            return true;
        }

        if (id == R.id.action_ringtone) {
            changeRingTone(findViewById(R.id.selectedRingToneText));
            return true;
        }

        if(id == R.id.action_try_alarm){
            launchAlarm();
            return true;
        }

        if(id == R.id.action_try_circles){
            launchCircles();
            return true;
        }

        if(id == R.id.action_try_shapes){
            launchShapes();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Appelée par le layout lors du click sur l'heure
     * @param view
     */
    public void changeTime(View view){
        FeedBackAnimation.feedBack(view);
        Alarm alarm = Configuration.load(this);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, alarm.getHour(), alarm.getMinutes(), true);
        timePickerDialog.show();
    }

    /**
     * Appelée par le layout lors du click sur le switch
     * @param view
     */
    public void toogleAlarm(View view) {
        Alarm alarm = Configuration.load(this);
        toogleAlarm(view, alarm);
    }
    /**
     * Appelée par le layout lors du click sur le switch
     * @param view
     */
    public void toogleAlarm(View view, Alarm alarm){
        Switch switcher = (Switch)view;
        alarm.setActivated(switcher.isChecked());
        if(alarm.isActivated()){
            // Set alarm
            AlarmScheduler.schedule(this, alarm);
            String prochainJour = DayHelper.toString(this, alarm.getDayNumber());
            String text = MessageFormat.format(getString(R.string.alarmscheduled), prochainJour, alarm.toString());
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        } else {
            // cancel alarm
            AlarmScheduler.cancel(this);
            Toast.makeText(this, getString(R.string.alarmcanceled), Toast.LENGTH_SHORT).show();
        }
        Configuration.save(this, alarm);
        updateNextAlarmText(findViewById(R.id.nextAlarm), alarm);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Alarm alarm = Configuration.load(this);
        alarm.setHour(hourOfDay);
        alarm.setMinutes(minute);
        TextView alarmValue = (TextView)findViewById(R.id.alarmValue);
        alarmValue.setText(alarm.toString());

        Switch switcher = (Switch)findViewById(R.id.alarmSwitcher);
        switcher.setChecked(true);
        toogleAlarm(switcher, alarm);
    }

    public void launchAlarm(){
        AlarmExecutor.INSTANCE.startAlarm(this, Configuration.load(this));
        startActivity(new Intent(this, AlarmActivity.class));
    }

    public void changeRingTone(View view){
        Alarm alarm = Configuration.load(this);
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getString(R.string.ringtoneselectiontitle));
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_ALARM);
        if(alarm.getSelectedRingtone()!=null) {
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(alarm.getSelectedRingtone()));
        }
        startActivityForResult(intent, RINGTONE_SELECTION_TASK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch(requestCode){
                case RINGTONE_SELECTION_TASK:
                    Uri ringtone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    if(ringtone!=null) {
                        Alarm alarm = Configuration.load(this);
                        alarm.setSelectedRingtone(ringtone.toString());
                        Configuration.save(this, alarm);
                        updateSelectedRingtone(alarm);
                    }
                    break;
                default:break;
            }
        }
    }

    public void updateNextAlarmText(View v){
        updateNextAlarmText(v, Configuration.load(this));
    }

    public void updateNextAlarmText(View v, Alarm alarm){
        TextView nextAlarm = (TextView)v;
        if(alarm.isActivated()) {
            String message = getString(R.string.nextalarm);
            message = MessageFormat.format(message, DayHelper.toString(this, alarm.getDayNumber()), alarm.toString());
            nextAlarm.setText(message);
        } else {
            nextAlarm.setText(getString(R.string.alarmcanceled));
        }
    }

    public void launchShapes(){
        startActivity(new Intent(this, ShapesActivity.class));
    }

    public void launchCircles(){
        startActivity(new Intent(this, CirclesActivity.class));
    }
}
