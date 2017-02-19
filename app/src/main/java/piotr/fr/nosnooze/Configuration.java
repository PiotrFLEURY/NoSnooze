package piotr.fr.nosnooze;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import piotr.fr.nosnooze.dataobjects.Alarm;

/**
 * Created by Piotr on 08/02/2015.
 */
public class Configuration {

    private static final String FILENAME = "nosnooze.config";



    public static Alarm load(Context context){
        Alarm alarm=new Alarm();
        FileInputStream inputStream;
        ObjectInputStream ois;
        try {
            inputStream = context.openFileInput(FILENAME);
            ois = new ObjectInputStream(inputStream);
            alarm = (Alarm)ois.readObject();
            ois.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alarm;
    }

    public static void save(Context context, Alarm alarm){
        FileOutputStream outputStream;
        ObjectOutputStream oos;
        try {
            outputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(outputStream);
            oos.writeObject(alarm);
            oos.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
