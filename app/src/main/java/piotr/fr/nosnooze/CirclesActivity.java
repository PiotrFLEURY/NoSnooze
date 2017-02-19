package piotr.fr.nosnooze;

import android.graphics.Color;
import android.graphics.Outline;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import piotr.fr.nosnooze.dataobjects.FeedBackAnimation;


public class CirclesActivity extends ActionBarActivity implements View.OnClickListener {

    private List<Button> buttons=new ArrayList<Button>();

    private int next = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circles);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        buttons=new ArrayList<Button>();
        next = 1;

        GridLayout layout = (GridLayout)findViewById(R.id.circleactivitylayout);

        List<Integer> numbers = new ArrayList<Integer>(){{
            add(5);
            add(4);
            add(3);
            add(2);
            add(1);
        }};

        while(numbers.size()>0){
            int index = (int)(Math.random()*numbers.size());
            int currentNumber=numbers.get(index);
            createCircle(currentNumber, layout);
            numbers.remove(index);
        }

    }

    private void createCircle(int number, GridLayout layout){
        final Button button = (Button)getLayoutInflater().inflate(R.layout.circularbutton, layout, false);
        button.setText("" + number);
        button.setOnClickListener(this);
        layout.addView(button);
        buttons.add(button);
    }

    @Override
    public void onBackPressed() {
        //
    }

    private boolean allSelected(){
        for(Button btn:buttons){
            if(!btn.isSelected()){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        if(next==Integer.valueOf(button.getText().toString())) {
            button.setSelected(true);
            button.setTextColor(Color.WHITE);
            if(allSelected()){
                AlarmExecutor.INSTANCE.stopAlarm();
                finish();
            } else {
                next++;
            }
        } else {
            for(Button btn:buttons){
                btn.setSelected(false);
                btn.setTextColor(Color.BLACK);
            }
            next=1;
        }
    }
}
