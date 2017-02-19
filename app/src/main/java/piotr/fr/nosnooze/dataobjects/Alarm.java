package piotr.fr.nosnooze.dataobjects;

import android.net.Uri;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Piotr on 07/02/2015.
 */
public class Alarm implements Serializable {

    private static final long serialVersionUID = 4016888534089558885L;
    int hour=0;
    int minutes=0;
    SortedSet<Integer> days;
    boolean activated;
    String selectedRingtone;

    public Alarm(){
        hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        minutes = Calendar.getInstance().get(Calendar.MINUTE);
        days = new TreeSet<Integer>();
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getHour(){
        return hour;
    }

    public int getMinutes(){
        return minutes;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    private String pad(int val){
        String result = String.valueOf(val);
        if(result.length()<2){
            result = "0"+result;
        }
        return result;
    }

    public void addDay(Integer day){
        days.add(day);
    }

    public void removeDay(Integer day){
        days.remove(day);
    }

    public SortedSet<Integer> getDays() {
        return days;
    }

    public boolean hasDaysSelected(){
        return !days.isEmpty();
    }

    public boolean isDaySelected(Integer dayNumber){
        return days.contains(dayNumber);
    }

    public String getSelectedRingtone() {
        return selectedRingtone;
    }

    public void setSelectedRingtone(String selectedRingtone) {
        this.selectedRingtone = selectedRingtone;
    }

    @Override
    public String toString() {
        return pad(getHour())+":"+pad(getMinutes());
    }

    public Calendar toCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, getHour());
        calendar.set(Calendar.MINUTE, getMinutes());
        calendar.set(Calendar.SECOND, 0);
        if(Calendar.getInstance().getTime().after(calendar.getTime())){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if(hasDaysSelected()) {
            while (!isDaySelected(calendar.get(Calendar.DAY_OF_WEEK))) {
                calendar.add(Calendar.DAY_OF_WEEK, 1);
            }
        }

        return calendar;
    }

    public Integer getDayNumber() {
        return toCalendar().get(Calendar.DAY_OF_WEEK);
    }
}
