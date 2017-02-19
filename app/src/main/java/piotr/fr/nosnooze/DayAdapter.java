package piotr.fr.nosnooze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

import piotr.fr.nosnooze.dataobjects.Alarm;

/**
 * Created by Piotr on 08/02/2015.
 */
public class DayAdapter extends ArrayAdapter<Integer>  {

    Context context;
    Integer[] values;
    Alarm alarm;

    public DayAdapter(Context context, Integer[] values) {
        super(context, R.layout.dayselector, values);
        this.context = context;
        this.values = values;
        alarm = Configuration.load(context);
    }

    @Override
    public int getCount() {
        return values.length;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.dayselector, parent, false);
        TextView label = (TextView)rowView.findViewById(R.id.daylabel);

        final Integer currentDay = values[position];
        String text = DayHelper.toString(context, currentDay);
        label.setText(text);
        final CheckBox cb = (CheckBox)rowView.findViewById(R.id.dayselectioncheckbox);
        cb.setChecked(alarm.isDaySelected(currentDay));
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb.isChecked()){
                    alarm.addDay(currentDay);
                } else {
                    alarm.removeDay(currentDay);
                }
                Configuration.save(context, alarm);
            }
        });

        return rowView;
    }

}
