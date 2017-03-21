package com.jefersonsilva.androidthings;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;


/**
 * Created by jefrsilva on 21/03/17.
 */

public class Button {

    private Gpio gpio;
    private int color;
    private ButtonCallback callback;

    public Button(PeripheralManagerService service, String port, int color) {
        this.color = color;
        try {
            this.gpio = service.openGpio(port);
            this.gpio.setDirection(Gpio.DIRECTION_IN);
            this.gpio.setEdgeTriggerType(Gpio.EDGE_BOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCallback(ButtonCallback callback) {
        this.callback = callback;
        try {
            this.gpio.registerGpioCallback(callback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getColor() {
        return color;
    }

    public void close() {
        this.gpio.unregisterGpioCallback(callback);
        try {
            this.gpio.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
