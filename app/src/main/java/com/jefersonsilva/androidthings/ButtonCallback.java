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

    public ButtonCallback(GeniusRunnable geniusRunnable) {
        this.runnable = geniusRunnable;
    }

    @Override
    public boolean onGpioEdge(Gpio gpio) {
        Log.i(TAG, "GPIO Changed, button pressed/released");
        try {
            runnable.buttonChanged(!gpio.getValue());
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }

        return true;

    }
}
