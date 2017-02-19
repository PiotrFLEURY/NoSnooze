package piotr.fr.nosnooze;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by Piotr on 08/02/2015.
 */
public class DayHelper {

    public static String toString(Context context, Integer dayNumber){
        String text=null;
        switch(dayNumber){
            case Calendar.SUNDAY:
                text=context.getString(R.string.Dimanche);
                break;
            case Calendar.MONDAY:
                text=context.getString(R.string.Lundi);
                break;
            case Calendar.TUESDAY:
                text=context.getString(R.string.Mardi);
                break;
            case Calendar.WEDNESDAY:
                text=context.getString(R.string.Mercredi);
                break;
            case Calendar.THURSDAY:
                text=context.getString(R.string.Jeudi);
                break;
            case Calendar.FRIDAY:
                text=context.getString(R.string.Vendredi);
                break;
            case Calendar.SATURDAY:
                text=context.getString(R.string.Samedi);
                break;
        }
        return text;
    }
}
