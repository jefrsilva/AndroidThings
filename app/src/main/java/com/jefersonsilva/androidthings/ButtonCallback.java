package com.jefersonsilva.androidthings;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;

import java.io.IOException;

/**
 * Created by jefrsilva on 03/03/17.
 */

public class ButtonCallback extends GpioCallback {

    private static final String TAG = "FOCAFOCA";

    private final GeniusRunnable runnable;
    private int color;

    public ButtonCallback(GeniusRunnable geniusRunnable, int color) {
        this.runnable = geniusRunnable;
        this.color = color;
    }

    @Override
    public boolean onGpioEdge(Gpio gpio) {
        try {
            Log.i(TAG, "GPIO Changed, button pressed/released " + gpio.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (!gpio.getValue()) {
                runnable.buttonPressed(color);
            }

        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }

        return true;

    }
}
