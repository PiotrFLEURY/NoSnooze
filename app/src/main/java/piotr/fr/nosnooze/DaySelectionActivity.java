package piotr.fr.nosnooze;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import piotr.fr.nosnooze.dataobjects.Alarm;
import piotr.fr.nosnooze.dataobjects.FeedBackAnimation;


public class DaySelectionActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_selection);

        ListView list = (ListView)findViewById(R.id.dayslistview);
        list.setOnItemClickListener(this);
        Integer[] days = new Integer[]{Calendar.SUNDAY,
                Calendar.MONDAY,
                Calendar.TUESDAY,
                Calendar.WEDNESDAY,
                Calendar.THURSDAY,
                Calendar.FRIDAY,
                Calendar.SATURDAY};
        list.setAdapter(new DayAdapter(this, days));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FeedBackAnimation.feedBack(view);
    }

    @Override
    public void onBackPressed() {
        Alarm alarm = Configuration.load(this);
        if(!alarm.hasDaysSelected()){
            alarm.setActivated(false);
            Configuration.save(this, alarm);
        }
        super.onBackPressed();
    }
}
