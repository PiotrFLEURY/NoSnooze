package piotr.fr.nosnooze;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import piotr.fr.nosnooze.dataobjects.ColoredShape;
import piotr.fr.nosnooze.dataobjects.FeedBackAnimation;


public class ShapesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        final int max = (ColoredShape.Color.values().length * ColoredShape.Shape.values().length)/2;

        final ColoredShape target = new ColoredShape();

        updateInstructionText(target);

        List<ColoredShape> shapes = new ArrayList<ColoredShape>(){{
            add(new ColoredShape(ColoredShape.Color.BLUE, ColoredShape.Shape.CIRCLE));
            add(new ColoredShape(ColoredShape.Color.GREEN, ColoredShape.Shape.CIRCLE));
            add(new ColoredShape(ColoredShape.Color.RED, ColoredShape.Shape.CIRCLE));
            add(new ColoredShape(ColoredShape.Color.WHITE, ColoredShape.Shape.CIRCLE));

            add(new ColoredShape(ColoredShape.Color.BLUE, ColoredShape.Shape.SQUARE));
            add(new ColoredShape(ColoredShape.Color.GREEN, ColoredShape.Shape.SQUARE));
            add(new ColoredShape(ColoredShape.Color.RED, ColoredShape.Shape.SQUARE));
            add(new ColoredShape(ColoredShape.Color.WHITE, ColoredShape.Shape.SQUARE));
        }};


        GridLayout content = (GridLayout) findViewById(R.id.shapesactivitylayout);

        final List<ColoredShape> touched = new ArrayList<ColoredShape>();
        while(!shapes.isEmpty()) {
            int index = (int) (Math.random()*shapes.size());
            final ColoredShape current = shapes.get(index);
            int drawableResourceId = this.getResources().getIdentifier(current.getResourceName(), "drawable", this.getPackageName());
            final Button shapeButton = (Button) getLayoutInflater().inflate(R.layout.shapebutton, content, false);
            shapeButton.setBackgroundResource(drawableResourceId);
            shapeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (current.equals(target)) {
                        if(max>touched.size()){
                            final Animation animScale = AnimationUtils.loadAnimation(ShapesActivity.this, R.anim.anim_scale);
                            shapeButton.startAnimation(animScale);
                            touched.add(current);
                            target.shake();
                            updateInstructionText(target);
                        } else {
                            AlarmExecutor.INSTANCE.stopAlarm();
                            finish();
                        }
                    } else {
                        final Animation animationWizz = AnimationUtils.loadAnimation(ShapesActivity.this, R.anim.anim_wizz);
                        shapeButton.startAnimation(animationWizz);
                    }
                }
            });
            content.addView(shapeButton);
            shapes.remove(current);
        }
    }

    private void updateInstructionText(ColoredShape target) {
        TextView shapeName = (TextView)findViewById(R.id.shapesactivity_shapeName);
        shapeName.setText(target.shape.descritpionResource);
        TextView shapeColor = (TextView)findViewById(R.id.shapesactivity_shapeColor);
        shapeColor.setText(target.color.descritpionResource);
    }

    @Override
    public void onBackPressed() {
        //
    }
}
