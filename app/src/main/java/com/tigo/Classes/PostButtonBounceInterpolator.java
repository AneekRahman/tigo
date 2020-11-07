package com.tigo.Classes;

public class PostButtonBounceInterpolator implements android.view.animation.Interpolator{

    private double mAmplitude = 1;
    private double mFrequency = 10;

    public PostButtonBounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }

}
