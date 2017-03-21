package com.jefersonsilva.androidthings;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

/**
 * Created by jefrsilva on 21/03/17.
 */

public class Led {

    private Gpio gpio;

    public Led(PeripheralManagerService service, String port) {
        try {
            this.gpio = service.openGpio(port);
            this.gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toggle() {
        try {
            this.gpio.setValue(!this.gpio.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.gpio.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
