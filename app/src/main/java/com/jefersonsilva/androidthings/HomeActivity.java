package com.jefersonsilva.androidthings;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "FOCAFOCA";
    private static final String GPIO_PIN_LED = "BCM17";
    private static final String GPIO_PIN_BUTTON = "BCM27";

    public Handler handler = new Handler();

    private Gpio ledGpio;
    private Gpio buttonGpio;

    private com.google.android.things.pio.GpioCallback buttonCallback;
    private GeniusRunnable geniusRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PeripheralManagerService service = new PeripheralManagerService();
        Log.d("FOCAFOCA", "Available GPIO: " + service.getGpioList());

        try {
            ledGpio = service.openGpio(GPIO_PIN_LED);
            ledGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            Log.i(TAG, "LED Value: " + ledGpio.getValue());

            handler.post(new GeniusRunnable(ledGpio));

            buttonGpio = service.openGpio(GPIO_PIN_BUTTON);
            buttonGpio.setDirection(Gpio.DIRECTION_IN);
            buttonGpio.setEdgeTriggerType(Gpio.EDGE_BOTH);
            buttonGpio.registerGpioCallback(new ButtonCallback(geniusRunnable));
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(geniusRunnable);

        if (buttonGpio != null) {
            buttonGpio.unregisterGpioCallback(buttonCallback);
            try {
                buttonGpio.close();
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            }
        }

        if (ledGpio != null) {
            try {
                ledGpio.close();
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            }
        }
    }
}
