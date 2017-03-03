package com.jefersonsilva.androidthings;

import com.google.android.things.pio.Gpio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jefrsilva on 03/03/17.
 */

public class GeniusRunnable implements Runnable {

    private final Gpio ledGpio;
    private int count = 0;

    private List<Integer> sequence = new ArrayList<>();

    GeniusRunnable(Gpio ledGpio) {
        this.ledGpio = ledGpio;
    }

    @Override
    public void run() {
        if (ledGpio == null) {
            return;
        }

        count++;
        sequence.clear();

        for (int blinks = 0; blinks < count; blinks++) {
            blink();
        }



        // handler.postDelayed(geniusRunnable, 1000);
    }

    private void blink() {
        try {
            ledGpio.setValue(true);
            Thread.sleep(500);
            ledGpio.setValue(false);

            sequence.add(1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void buttonChanged(boolean value) {

    }
}
