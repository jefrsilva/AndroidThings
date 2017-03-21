package com.jefersonsilva.androidthings;

import android.util.Log;

import com.google.android.things.pio.Gpio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jefrsilva on 03/03/17.
 */

public class GeniusRunnable implements Runnable {

    private final List<Led> leds;

    private int sequencePosition = 0;

    private List<Integer> sequence = new ArrayList<>();

    private GameState state = GameState.TEACHING;
    private long startWaiting;
    private boolean isFinished = false;

    GeniusRunnable(List<Led> leds) {
        this.leds = leds;
    }

    @Override
    public void run() {
        while (!isFinished) {
            switch(state) {
                case TEACHING:
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int nextColor = (int) (Math.random() * 4);
                    sequence.add(nextColor);

                    for (Integer color : sequence) {
                        blink(color);
                    }

                    sequencePosition = 0;

                    Log.i("FOCAFOCA", sequence.toString());
                    state = GameState.WAITING;
                    this.startWaiting = System.currentTimeMillis();

                    break;

                case WAITING:
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - startWaiting > 5000 + (1000 * sequence.size())) {
                        if (sequencePosition < sequence.size()) {
                            state = GameState.GAMEOVER;
                        }

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case GAMEOVER:
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    sequence.clear();
                    state = GameState.TEACHING;
                    break;
            }
        }
    }

    private void blink(int color) {
        try {
            Led led = leds.get(color);
            Thread.sleep(350);
            led.toggle();
            Thread.sleep(350);
            led.toggle();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void buttonPressed(int value) {
        Log.i("FOCAFOCA", "Botao pressionado : " + value);

        if (state == GameState.WAITING) {
            if (sequence.get(sequencePosition) == value) {
                sequencePosition++;
                if (sequencePosition == sequence.size()) {
                    state = GameState.TEACHING;
                }
            } else {
                state = GameState.GAMEOVER;
            }
        }
    }
}
