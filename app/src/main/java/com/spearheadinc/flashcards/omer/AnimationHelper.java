package com.spearheadinc.flashcards.omer;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

public class AnimationHelper {
    public void animate(float start, float end, final View view) {
        final float centerX = view.getWidth() / 2.0f;
        final float centerY = view.getHeight() / 2.0f;
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 100.0f, false);
        rotation.setDuration(500);
        rotation.setFillAfter(false);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(rotation);
    }
}
