package piotr.fr.nosnooze.dataobjects;

import android.animation.Animator;
import android.view.View;
import android.view.ViewAnimationUtils;

import piotr.fr.nosnooze.R;

/**
 * Created by Piotr on 08/02/2015.
 */
public class FeedBackAnimation {

    public static void feedBack(View myView){
        // get the center for the clipping green_circle
        int cx = (myView.getLeft() + myView.getRight()) / 2;
        int cy = (myView.getTop() + myView.getBottom()) / 2;

        // get the final radius for the clipping green_circle
        int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        //myView.setVisibility(View.VISIBLE);
        anim.start();
    }
}
